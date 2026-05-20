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

import pe.edu.uni.smartlibrary.model.Usuario;
import pe.edu.uni.smartlibrary.repository.RepositoryUsuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private RepositoryUsuario repo;

    // LISTAR TODOS LOS USUARIOS
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        Usuario usuario = repo.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    // REGISTRAR NUEVO USUARIO
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuario.getPuntos() < 0) {
            return ResponseEntity.badRequest().body("Los puntos no pueden ser negativos");
        }
        
        Usuario guardado = repo.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (usuario.getPuntos() < 0) {
            return ResponseEntity.badRequest().body("Los puntos no pueden ser negativos");
        }

        Optional<Usuario> usuarioOpt = repo.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario actual = usuarioOpt.get();
            actual.setNombre(usuario.getNombre());
            actual.setTipo(usuario.getTipo());
            actual.setPuntos(usuario.getPuntos());
            
            Usuario actualizado = repo.save(actual);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR USUARIO
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