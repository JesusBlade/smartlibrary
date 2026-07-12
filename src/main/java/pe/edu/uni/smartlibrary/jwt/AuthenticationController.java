package pe.edu.uni.smartlibrary.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.uni.smartlibrary.model.Usuario;
import pe.edu.uni.smartlibrary.repository.RepositoryUsuario;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RepositoryUsuario repoUsuario;

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
        System.out.println("[AUTH] Peticion de autenticacion recibida");
        System.out.println("[AUTH] Usuario recibido: " + (authRequest != null ? authRequest.getUser() : "null"));
        System.out.println("[AUTH] Password recibido: " + (authRequest != null ? authRequest.getPassword() : "null"));

        if (authRequest == null || authRequest.getUser() == null) {
            System.out.println("[AUTH] Error: AuthRequest o usuario nulo");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos de autenticación incompletos");
        }

        Usuario usuario = repoUsuario.findByUser(authRequest.getUser());
        if (usuario != null) {
            System.out.println("[AUTH] Usuario encontrado en BD: " + usuario.getUser());
            System.out.println("[AUTH] Password en BD: " + usuario.getPassword());
            System.out.println("[AUTH] ¿Coinciden passwords?: " + usuario.getPassword().equals(authRequest.getPassword()));
        } else {
            System.out.println("[AUTH] Usuario NO encontrado en BD para: " + authRequest.getUser());
        }

        if (usuario != null && usuario.getPassword().equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getUser());
            System.out.println("[AUTH] Autenticación EXITOSA. Token generado: " + token);
            return ResponseEntity.ok(token);
        } else {
            System.out.println("[AUTH] Autenticación FALLIDA");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}