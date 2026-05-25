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

        Usuario usuario = repoUsuario.findByUser(authRequest.getUser());

        if (usuario != null && usuario.getPassword().equals(authRequest.getPassword())) {

            String token = jwtUtil.generateToken(authRequest.getUser());
            return ResponseEntity.ok(token);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}