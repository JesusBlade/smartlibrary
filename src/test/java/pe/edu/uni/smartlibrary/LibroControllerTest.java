package pe.edu.uni.smartlibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // LISTA VACÍA → 204
    @Test
    void listar_conLibros_deberiaRetornar200() throws Exception {
        mockMvc.perform(get("/libros"))
                .andExpect(status().isOk());
    }

    // CREAR LIBRO → 201
    @Test
    void registrar_libroValido_deberiaRetornar201() throws Exception {
        mockMvc.perform(post("/libros")
                .contentType("application/json")
                .content("""
                    {
                      "titulo": "Libro Test",
                      "autor": "Autor X",
                      "paginas": 100
                    }
                """))
            .andExpect(status().isCreated());
    }

    // ERROR → 400 (VALIDACIÓN)
    @Test
    void registrar_libroPaginasInvalidas_deberiaRetornar400() throws Exception {
        mockMvc.perform(post("/libros")
                .contentType("application/json")
                .content("""
                    {
                      "titulo": "Error",
                      "autor": "Autor",
                      "paginas": 0
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // NO EXISTE → 404
    @Test
    void obtener_libroNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/libros/999"))
                .andExpect(status().isNotFound());
    }
}