package pe.edu.uni.smartlibrary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.smartlibrary.model.Libro;

@RestController
@RequestMapping("/libros")
public class LibroController {

	
	// lista en memoria
    private List<Libro> lista = new ArrayList<>();

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtener(@PathVariable("id") int id) {
        for (Libro l : lista) {
            if (l.getId() == id) {
                return ResponseEntity.ok(l); // 200
            }
        }
        return ResponseEntity.notFound().build(); // 404
    }

    // LISTAR
    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(lista);
    }

    // REGISTRAR
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Libro libro) {

        if (libro.getPaginas() <= 0) {
            return ResponseEntity.badRequest().body("Páginas inválidas"); // ✔️
        }

        libro.setId(lista.size() + 1);
        lista.add(libro);

        return ResponseEntity.status(201).body(libro);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable("id") int id, @RequestBody Libro libro) {

        // 🔥 VALIDACIÓN
        if (libro.getPaginas() <= 0) {
            return ResponseEntity.badRequest().build(); // 400
        }

        for (Libro l : lista) {
            if (l.getId() == id) {
                l.setTitulo(libro.getTitulo());
                l.setAutor(libro.getAutor());
                l.setPaginas(libro.getPaginas());
                return ResponseEntity.ok(l); // 200
            }
        }

        return ResponseEntity.notFound().build(); // 404
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity <String> eliminar(@PathVariable("id") int id) {
    	boolean eliminado = lista.removeIf(libro -> libro.getId() == id);

        if (eliminado) {
            return ResponseEntity.ok("Libro eliminado: " + id);
        }

        return ResponseEntity.notFound().build(); // 404
    }
}

