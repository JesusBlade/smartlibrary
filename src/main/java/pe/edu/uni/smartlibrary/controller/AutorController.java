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

import pe.edu.uni.smartlibrary.model.Autor;
import pe.edu.uni.smartlibrary.repository.RepositoryAutor;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private RepositoryAutor repo;

    // LISTAR TODOS LOS AUTORES
    @GetMapping
    public ResponseEntity<List<Autor>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER AUTOR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtener(@PathVariable("id") Long id) {
        Autor autor = repo.findById(id).orElse(null);

        if (autor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(autor);
    }

    // REGISTRAR NUEVO AUTOR
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Autor autor) {
        if (autor.getNombreAutor() == null || autor.getNombreAutor().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre del autor no puede estar vacío");
        }

        Autor guardado = repo.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // ACTUALIZAR AUTOR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @RequestBody Autor autor) {
        // VALIDACIÓN
        if (autor.getNombreAutor() == null || autor.getNombreAutor().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre del autor no puede estar vacío");
        }

        Autor actual = repo.findById(id).orElse(null);
        if (actual != null) {
            actual.setNombreAutor(autor.getNombreAutor());
            actual.setNacionalidad(autor.getNacionalidad());

            Autor actualizado = repo.save(actual);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR AUTOR
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
