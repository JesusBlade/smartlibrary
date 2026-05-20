package pe.edu.uni.smartlibrary.controller;

import java.util.List;
import java.util.Optional;

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

import pe.edu.uni.smartlibrary.model.Prestamo;
import pe.edu.uni.smartlibrary.repository.RepositoryPrestamo;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private RepositoryPrestamo repo;

    // LISTAR TODOS LOS PRESTAMOS
    @GetMapping
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER PRESTAMO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtener(@PathVariable Long id) {
        Prestamo prestamo = repo.findById(id).orElse(null);

        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prestamo);
    }

    // REGISTRAR NUEVO PRESTAMO
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Prestamo prestamo) {
        if (prestamo.getDias() <= 0) {
            return ResponseEntity.badRequest().body("La cantidad de días debe ser mayor a 0");
        }
        
        Prestamo guardado = repo.save(prestamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // ACTUALIZAR PRESTAMO
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        if (prestamo.getDias() <= 0) {
            return ResponseEntity.badRequest().body("La cantidad de días debe ser mayor a 0");
        }

        Optional<Prestamo> prestamoOpt = repo.findById(id);
        if (prestamoOpt.isPresent()) {
            Prestamo actual = prestamoOpt.get();
            actual.setLibro(prestamo.getLibro());
            actual.setUsuario(prestamo.getUsuario());
            actual.setDias(prestamo.getDias());
            
            Prestamo actualizado = repo.save(actual);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR PRESTAMO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}