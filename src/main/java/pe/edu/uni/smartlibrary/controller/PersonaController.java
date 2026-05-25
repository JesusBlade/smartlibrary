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

import pe.edu.uni.smartlibrary.model.Persona;
import pe.edu.uni.smartlibrary.repository.RepositoryPersona;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private RepositoryPersona repo;

    // LISTAR TODAS LAS PERSONAS
    @GetMapping
    public ResponseEntity<List<Persona>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER PERSONA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtener(@PathVariable("id") Long id) {
        Persona persona = repo.findById(id).orElse(null);

        if (persona == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(persona);
    }

    // REGISTRAR NUEVA PERSONA
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Persona persona) {
        if (persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre no puede estar vacío");
        }
        
        if (persona.getDni() <= 0) {
            return ResponseEntity.badRequest().body("El DNI debe ser mayor a 0");
        }

        Persona guardada = repo.save(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // ACTUALIZAR PERSONA
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @RequestBody Persona persona) {
        // VALIDACIÓN
        if (persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre no puede estar vacío");
        }
        
        if (persona.getDni() <= 0) {
            return ResponseEntity.badRequest().body("El DNI debe ser mayor a 0");
        }

        Persona actual = repo.findById(id).orElse(null);
        if (actual != null) {
            actual.setNombre(persona.getNombre());
            actual.setApellido(persona.getApellido());
            actual.setDni(persona.getDni());
            actual.setCorreo(persona.getCorreo());

            Persona actualizada = repo.save(actual);
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR PERSONA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
