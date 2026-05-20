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

import pe.edu.uni.smartlibrary.model.Libro;
import pe.edu.uni.smartlibrary.repository.RepositoryLibro;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private RepositoryLibro repo;
    
    // LISTAR TODOS LOS LIBROS
    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }
    
    // OBTENER LIBRO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtener(@PathVariable Long id) {

        Libro libro = repo.findById(id).orElse(null);

        if (libro == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(libro);
    }
    
    // REGISTRAR NUEVO LIBRO
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Libro libro) {
        if (libro.getPaginas() <= 0) {
            return ResponseEntity.badRequest().body("La cantidad de páginas debe ser mayor a 0");
        }
        
        Libro guardado = repo.save(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    
    // ACTUALIZAR LIBRO
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        if (libro.getPaginas() <= 0) {
            return ResponseEntity.badRequest().body("La cantidad de páginas debe ser mayor a 0");
        }

        Optional<Libro> libroOpt = repo.findById(id);
        if (libroOpt.isPresent()) {
            Libro actual = libroOpt.get();
            actual.setTitulo(libro.getTitulo());
            actual.setAutor(libro.getAutor());
            actual.setPaginas(libro.getPaginas());
            
            Libro actualizado = repo.save(actual);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // ELIMINAR LIBRO
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