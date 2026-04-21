package pe.edu.uni.smartlibrary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import pe.edu.uni.smartlibrary.model.Libro;

@RestController
@RequestMapping("/libros")
public class LibroController {

	// Simula la consulta de un libro específico en la biblioteca
	@GetMapping("/{id}")
	public Libro obtener(@PathVariable("id") int id) {

	    Libro l = new Libro();
	    l.setId(id);
	    l.setTitulo("Libro " + id);
	    l.setAutor("Autor X");
	    l.setPaginas(200);

	    return l;
	}
	
	// Retorna una lista simulada de libros disponibles en la biblioteca
	@GetMapping
	public List<Libro> listar() {
	    List<Libro> lista = new ArrayList<>();

	    Libro l1 = new Libro();
	    l1.setId(1);
	    l1.setTitulo("Java Básico");
	    l1.setAutor("Autor 1");
	    l1.setPaginas(150);

	    lista.add(l1);

	    return lista;
	}
	
	// Registrar un nuevo libro
	@PostMapping
	public Libro registrar(@RequestBody Libro libro) {
	    libro.setId((int)(Math.random() * 1000));
	    return libro;
	}
	
	// Actualizar un libro existente
	@PutMapping("/{id}")
	public Libro actualizar(@PathVariable("id") int id, @RequestBody Libro libro) {
	    libro.setId(id);
	    return libro;
	}
	
	// Eliminar un libro por ID

	@DeleteMapping("/{id}")
	public String eliminar(@PathVariable("id") int id) {
	    return "Libro eliminado: " + id;
	}
}

