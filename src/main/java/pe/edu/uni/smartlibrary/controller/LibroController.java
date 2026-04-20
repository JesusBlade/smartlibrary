package pe.edu.uni.smartlibrary.controller;

import org.springframework.web.bind.annotation.*;
import pe.edu.uni.smartlibrary.model.Libro;

@RestController
@RequestMapping("/libros")
public class LibroController {

	@GetMapping("/{id}")
	public Libro obtener(@PathVariable("id") int id) {

	    Libro l = new Libro();
	    l.setId(id);
	    l.setTitulo("Libro " + id);
	    l.setAutor("Autor X");
	    l.setPaginas(200);

	    return l;
	}
}