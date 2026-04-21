package pe.edu.uni.smartlibrary.controller;
import java.util.ArrayList;
import java.util.List;

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
    
    //listar
    @GetMapping
    public List<Prestamo> listar() {
        List<Prestamo> lista = new ArrayList<>();

        Prestamo p1 = new Prestamo();
        p1.setId(1);
        p1.setLibro("Java Básico");
        p1.setUsuario("Juan");
        p1.setDias(5);

        lista.add(p1);

        return lista;
    }
    
    
    @PostMapping
    public Prestamo registrar(@RequestBody Prestamo prestamo) {
        prestamo.setId((int)(Math.random() * 1000));
        return prestamo;
    }
    
    
    
}