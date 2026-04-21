package pe.edu.uni.smartlibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SmartlibraryNegativeTests {

    @Autowired
    private MockMvc mockMvc;

    // 1. Calcular multa - Datos inválidos
    @Test
    void multa_datosInvalidos_negativo() throws Exception {
        // Entrada: -5 días retraso (inválido)
        mockMvc.perform(get("/api/multa/calcular/-5/2.5/0/ESTUDIANTE"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos inválidos"));
    }

    @Test
    void multa_costoInvalido_negativo() throws Exception {
        // Entrada: -2.5 costo por día (inválido)
        mockMvc.perform(get("/api/multa/calcular/5/-2.5/0/ESTUDIANTE"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos inválidos"));
    }

    // 2. Días para leer un libro - Datos inválidos
    @Test
    void lectura_paginasInvalidas_negativo() throws Exception {
        // Entrada: 0 páginas (inválido)
        mockMvc.perform(get("/api/lectura/dias/0/30/1/2.0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos inválidos"));
    }

    @Test
    void lectura_velocidadInvalida_negativo() throws Exception {
        // Entrada: -10 páginas por día (inválido)
        mockMvc.perform(get("/api/lectura/dias/300/-10/1/2.0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos inválidos"));
    }

    // 3. Libros por mes - Datos inválidos
    @Test
    void librosMes_datosInvalidos_negativo() throws Exception {
        // Entrada: 0 páginas por libro
        mockMvc.perform(get("/api/lectura/libros-mes/0/20/30/1.0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos inválidos"));
    }

    // 4. Libros disponibles - Excede total
    @Test
    void librosDisponibles_excedeTotal_negativo() throws Exception {
        // Entrada: 10 total, 5 prestados, 5 reservados, 5 mantenimiento (Suma 15 > 10)
        mockMvc.perform(get("/api/libros/disponibles/10/5/5/5"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Valores exceden total"));
    }

    @Test
    void librosDisponibles_datosNegativos_negativo() throws Exception {
        // Entrada: -10 total
        mockMvc.perform(get("/api/libros/disponibles/-10/0/0/0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos inválidos"));
    }

    // Test de ruta inexistente (Error 404)
    @Test
    void rutaInexistente_negativo() throws Exception {
        mockMvc.perform(get("/api/ruta/que/no/existe"))
                .andExpect(status().isNotFound());
    }

    // Test de tipo de dato incorrecto (Error 400 por Spring)
    @Test
    void tipoDatoIncorrecto_negativo() throws Exception {
        // Se espera un entero para días, se envía texto
        mockMvc.perform(get("/api/multa/calcular/texto/2.5/0/ESTUDIANTE"))
                .andExpect(status().isBadRequest());
    }
}
