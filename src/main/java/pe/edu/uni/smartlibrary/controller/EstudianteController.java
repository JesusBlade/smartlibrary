package pe.edu.uni.smartlibrary.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import pe.edu.uni.smartlibrary.model.Estudiante;

import java.util.Date;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

	@GetMapping("/ver/{id}")
	public ResponseEntity<Estudiante> obtener(@PathVariable("id") int id) {

        Estudiante e = new Estudiante();
        e.setId(id);
        e.setNombre("Estudiante " + id);
        e.setEmail("estudiante" + id + "@gmail.com");
        e.setEdad(20);

        return ResponseEntity.ok(e);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Estudiante> registrar(@RequestBody Estudiante estudiante) {

        Date ahora = new Date();
        estudiante.setId((int) ahora.getTime());

        return ResponseEntity.ok(estudiante);
    }
}