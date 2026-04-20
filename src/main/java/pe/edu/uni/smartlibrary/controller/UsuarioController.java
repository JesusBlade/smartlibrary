package pe.edu.uni.smartlibrary.controller;

import org.springframework.web.bind.annotation.*;
import pe.edu.uni.smartlibrary.model.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable ("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);
        u.setNombre("Usuario " + id);
        u.setTipo("ESTUDIANTE");
        u.setPuntos(100);

        return u;
    }
}