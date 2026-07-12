package pe.edu.uni.smartlibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private pe.edu.uni.smartlibrary.repository.RepositoryUsuario repoUsuario;

    @Test
    void debugDB() {
        System.out.println("=== DEBUG USUARIOS EN BD ===");
        repoUsuario.findAll().forEach(u -> {
            System.out.println("ID: " + u.getId() + ", User: " + u.getUser() + ", Password: " + u.getPassword());
        });
        System.out.println("============================");
    }

    // LISTAR → 200
    @Test
    void listar_conUsuarios_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk());
    }

    // CREAR USUARIO → 201
    @Test
    void registrar_usuarioValido_deberiaRetornar201() throws Exception {
        mockMvc.perform(post("/usuario")
                .contentType("application/json")
                .content("""
                    {
                      "idPersona": 1,
                      "user": "admin",
                      "password": "123",
                      "tipoUsuario": "ADMIN"
                    }
                """))
            .andExpect(status().isCreated());
    }

    // ERROR → 400 (VALIDACIÓN)
    @Test
    void registrar_usuarioInvalido_deberiaRetornar400() throws Exception {
        mockMvc.perform(post("/usuario")
                .contentType("application/json")
                .content("""
                    {
                      "idPersona": 1,
                      "user": "",
                      "password": "123",
                      "tipoUsuario": "ADMIN"
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // NO EXISTE → 404
    @Test
    void obtener_usuarioNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/usuario/999"))
                .andExpect(status().isNotFound());
    }
}
