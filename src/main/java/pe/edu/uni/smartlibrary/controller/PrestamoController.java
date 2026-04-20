package pe.edu.uni.smartlibrary.controller;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.smartlibrary.model.Prestamo;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @GetMapping("/{id}")
    public Prestamo obtener(@PathVariable ("id") int id) {

        Prestamo p = new Prestamo();
        p.setId(id);
        p.setLibro("Libro X");
        p.setUsuario("Usuario X");
        p.setDias(7);

        return p;
    }
}