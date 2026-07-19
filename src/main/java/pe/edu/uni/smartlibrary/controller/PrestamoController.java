package pe.edu.uni.smartlibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.smartlibrary.model.EstadoPrestamo;
import pe.edu.uni.smartlibrary.model.Prestamo;
import pe.edu.uni.smartlibrary.repository.RepositoryPrestamo;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {
	
	private static final double MULTA_POR_DIA = 5.0;

    @Autowired
    private RepositoryPrestamo repo;

    // LISTAR TODOS LOS PRESTAMOS
    @GetMapping
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER PRESTAMO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtener(@PathVariable("id") Long id) {
        Prestamo prestamo = repo.findById(id).orElse(null);

        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prestamo);
    }

    // REGISTRAR NUEVO PRESTAMO
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Prestamo prestamo) {
        if (prestamo.getMulta() < 0) {
            return ResponseEntity.badRequest().body("La multa debe ser mayor o igual a 0");
        }
        if (prestamo.getIdUsuario() == null || prestamo.getIdPersona() == null || prestamo.getIdLibro() == null) {
            return ResponseEntity.badRequest().body("Los IDs de usuario, persona y libro no pueden ser nulos");
        }
     // Todo préstamo nuevo inicia como ACTIVO y aún no tiene fecha de devolución real
        prestamo.setEstado(EstadoPrestamo.ACTIVO);
        prestamo.setFechaDevolucionReal(null);
        
        Prestamo guardado = repo.save(prestamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // ACTUALIZAR PRESTAMO
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @RequestBody Prestamo prestamo) {
    	System.out.println("ENTRÓ A ACTUALIZAR");
        // VALIDACIÓN
        if (prestamo.getMulta() < 0) {
            return ResponseEntity.badRequest().body("La multa debe ser mayor o igual a 0");
        }
        if (prestamo.getIdUsuario() == null || prestamo.getIdPersona() == null || prestamo.getIdLibro() == null) {
            return ResponseEntity.badRequest().body("Los IDs de usuario, persona y libro no pueden ser nulos");
        }

        Prestamo actual = repo.findById(id).orElse(null);
        if (actual != null) {
            actual.setIdUsuario(prestamo.getIdUsuario());
            actual.setIdPersona(prestamo.getIdPersona());
            actual.setIdLibro(prestamo.getIdLibro());
            actual.setFechaPrestamo(prestamo.getFechaPrestamo());
            actual.setFechaDevolucion(prestamo.getFechaDevolucion());
            actual.setMulta(prestamo.getMulta());
            
         

            Prestamo actualizado = repo.save(actual);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR PRESTAMO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // PROCESO DE NEGOCIO: REGISTRAR LA DEVOLUCIÓN DE UN PRÉSTAMO
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverPrestamo(@PathVariable("id") Long id) {
    	System.out.println("ENTRÓ A DEVOLVER");

        // Buscar el préstamo por su ID
        Prestamo prestamo = repo.findById(id).orElse(null);

        // Validar que el préstamo exista
        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }

        // Validar que el préstamo no haya sido devuelto anteriormente
        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            return ResponseEntity.badRequest().body("El préstamo ya fue devuelto.");
        }

        // Registrar la fecha real de devolución
        prestamo.setFechaDevolucionReal(LocalDate.now());

        // Cambiar el estado del préstamo a DEVUELTO
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        // Calcular los días de retraso
        long diasRetraso = ChronoUnit.DAYS.between(
                prestamo.getFechaDevolucion(),
                prestamo.getFechaDevolucionReal()
        );

        // Si devolvió antes o el mismo día, no hay retraso
        if (diasRetraso < 0) {
            diasRetraso = 0;
        }

        // Calcular la multa (S/ 5 por día de retraso)
        double multa = diasRetraso * MULTA_POR_DIA;

        // Registrar la multa
        prestamo.setMulta(multa);

        // Guardar los cambios
        repo.save(prestamo);

        // Retornar el préstamo actualizado
        return ResponseEntity.ok(prestamo);
    }
}