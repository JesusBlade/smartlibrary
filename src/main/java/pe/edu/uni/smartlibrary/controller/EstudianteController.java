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

import pe.edu.uni.smartlibrary.model.Estudiante;
import pe.edu.uni.smartlibrary.repository.RepositoryEstudiante;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private RepositoryEstudiante repo;

    // LISTAR TODOS LOS ESTUDIANTES
    @GetMapping
    public ResponseEntity<List<Estudiante>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER ESTUDIANTE POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtener(@PathVariable Long id) {
        Estudiante estudiante = repo.findById(id).orElse(null);

        if (estudiante == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(estudiante);
    }

    // REGISTRAR NUEVO ESTUDIANTE
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Estudiante estudiante) {
        if (estudiante.getEdad() <= 0) {
            return ResponseEntity.badRequest().body("La edad debe ser mayor a 0");
        }
        
        Estudiante guardado = repo.save(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // ACTUALIZAR ESTUDIANTE
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        if (estudiante.getEdad() <= 0) {
            return ResponseEntity.badRequest().body("La edad debe ser mayor a 0");
        }

        Optional<Estudiante> estudianteOpt = repo.findById(id);
        if (estudianteOpt.isPresent()) {
            Estudiante actual = estudianteOpt.get();
            actual.setNombre(estudiante.getNombre());
            actual.setEmail(estudiante.getEmail());
            actual.setEdad(estudiante.getEdad());
            
            Estudiante actualizado = repo.save(actual);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR ESTUDIANTE
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