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

import pe.edu.uni.smartlibrary.model.Categoria;
import pe.edu.uni.smartlibrary.repository.RepositoryCategoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private RepositoryCategoria repo;

    // LISTAR TODAS LAS CATEGORIAS
    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER CATEGORIA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable("id") Long id) {
        Categoria categoria = repo.findById(id).orElse(null);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }

    // REGISTRAR NUEVA CATEGORIA
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Categoria categoria) {
        if (categoria.getCategoria() == null || categoria.getCategoria().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La categoría no puede estar vacía");
        }

        Categoria guardada = repo.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // ACTUALIZAR CATEGORIA
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
        // VALIDACIÓN
        if (categoria.getCategoria() == null || categoria.getCategoria().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La categoría no puede estar vacía");
        }

        Categoria actual = repo.findById(id).orElse(null);
        if (actual != null) {
            actual.setCategoria(categoria.getCategoria());

            Categoria actualizada = repo.save(actual);
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR CATEGORIA
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
