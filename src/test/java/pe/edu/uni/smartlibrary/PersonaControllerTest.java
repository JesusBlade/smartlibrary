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
public class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // LISTAR → 200
    @Test
    void listar_conPersonas_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/persona"))
                .andExpect(status().isOk());
    }

    // CREAR PERSONA → 201
    @Test
    void registrar_personaValida_deberiaRetornar201() throws Exception {
        mockMvc.perform(post("/persona")
                .contentType("application/json")
                .content("""
                    {
                      "nombre": "Juan",
                      "apellido": "Perez",
                      "dni": 12345678,
                      "correo": "juan@gmail.com"
                    }
                """))
            .andExpect(status().isCreated());
    }

    // ERROR → 400 (VALIDACIÓN)
    @Test
    void registrar_personaInvalida_deberiaRetornar400() throws Exception {
        mockMvc.perform(post("/persona")
                .contentType("application/json")
                .content("""
                    {
                      "nombre": "",
                      "apellido": "Perez",
                      "dni": 12345678,
                      "correo": "juan@gmail.com"
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // NO EXISTE → 404
    @Test
    void obtener_personaNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/persona/999"))
                .andExpect(status().isNotFound());
    }
}
