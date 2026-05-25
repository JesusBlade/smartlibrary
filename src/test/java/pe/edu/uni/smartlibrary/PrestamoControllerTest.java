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
public class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // LISTAR → 200
    @Test
    void listar_conPrestamos_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/prestamo"))
                .andExpect(status().isOk());
    }

    // CREAR PRESTAMO → 201
    @Test
    void registrar_prestamoValido_deberiaRetornar201() throws Exception {
        mockMvc.perform(post("/prestamo")
                .contentType("application/json")
                .content("""
                    {
                      "idUsuario": 1,
                      "idPersona": 1,
                      "idLibro": 1,
                      "multa": 0
                    }
                """))
            .andExpect(status().isCreated());
    }

    // ERROR → 400 (VALIDACIÓN)
    @Test
    void registrar_prestamoInvalido_deberiaRetornar400() throws Exception {
        mockMvc.perform(post("/prestamo")
                .contentType("application/json")
                .content("""
                    {
                      "idUsuario": 1,
                      "idPersona": 1,
                      "idLibro": 1,
                      "multa": -10
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // NO EXISTE → 404
    @Test
    void obtener_prestamoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/prestamo/999"))
                .andExpect(status().isNotFound());
    }
}
