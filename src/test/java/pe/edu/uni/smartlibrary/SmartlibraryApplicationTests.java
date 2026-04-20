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
public class SmartlibraryApplicationTests {

	@Autowired
	MockMvc mockMvc;

//1.- Calcular multa

	@Test
	void multa_estudianteSinDiasGracia() throws Exception {
		// Entrada: 5 días retraso, S/2.5 por día, 0 días gracia, ESTUDIANTE
		// Esperado: 5 * 2.5 = 12.5
		mockMvc.perform(get("/api/multa/calcular/5/2.5/0/ESTUDIANTE")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("12.5")));
	}

	@Test
	void multa_estudianteConDiasGracia() throws Exception {
		// Entrada: 5 días retraso, S/2.5 por día, 2 días gracia, ESTUDIANTE
		// Esperado: (5-2) * 2.5 = 7.5
		mockMvc.perform(get("/api/multa/calcular/5/2.5/2/ESTUDIANTE")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("7.5")));
	}

	@Test
	void multa_docenteConDescuento50() throws Exception {
		// Entrada: 4 días retraso, S/2.0 por día, 0 días gracia, DOCENTE (50%
		// descuento)
		// Esperado: 4 * 2.0 * 0.5 = 4.0
		mockMvc.perform(get("/api/multa/calcular/4/2.0/0/DOCENTE")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("4.0")));
	}

// 2. Días para leer un libro

	@Test
	void lectura_diasLibroNormalDificultad1() throws Exception {
		// Entrada: 300 páginas, 30 páginas/día, dificultad 1, 2h disponibles
		// Esperado: respuesta exitosa con número de días
		mockMvc.perform(get("/api/lectura/dias/300/30/1/2.0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void lectura_diasLibroGrandeDificultad3() throws Exception {
		// Entrada: 600 páginas, 20 páginas/día, dificultad 3 (más lento), 1h disponible
		// Esperado: respuesta exitosa
		mockMvc.perform(get("/api/lectura/dias/600/20/3/1.0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void lectura_diasLibroPequenio() throws Exception {
		// Entrada: 100 páginas, 50 páginas/día, dificultad 1, 3h disponibles
		// Esperado: respuesta exitosa
		mockMvc.perform(get("/api/lectura/dias/100/50/1/3.0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

// 3. Libros que pueden leer en un mes 

	@Test
	void librosMes_lecturaEstandar() throws Exception {
		// Entrada: 200 páginas/libro, 20 páginas/día, 30 días/mes, concentración 1.0
		// Esperado: respuesta exitosa
		mockMvc.perform(get("/api/lectura/libros-mes/200/20/30/1.0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void librosMes_lectorRapido() throws Exception {
		// Entrada: 150 páginas/libro, 50 páginas/día, 30 días/mes, concentración 1.2
		// Esperado: respuesta exitosa
		mockMvc.perform(get("/api/lectura/libros-mes/150/50/30/1.2")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void librosMes_pocosDiasLectura() throws Exception {
		// Entrada: 300 páginas/libro, 10 páginas/día, 10 días/mes, concentración 0.8
		// Esperado: respuesta exitosa
		mockMvc.perform(get("/api/lectura/libros-mes/300/10/10/0.8")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

// 4. Libros disponibles

	@Test
	void libros_disponiblesTodos() throws Exception {
		// Entrada: 10 total, 0 prestados, 0 reservados, 0 mantenimiento
		// Esperado: 10 disponibles
		mockMvc.perform(get("/api/libros/disponibles/10/0/0/0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("10")));
	}

	@Test
	void libros_disponiblesParcial() throws Exception {
		// Entrada: 10 total, 3 prestados, 1 reservado, 1 mantenimiento
		// Esperado: 5 disponibles
		mockMvc.perform(get("/api/libros/disponibles/10/3/1/1")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("5")));
	}

	@Test
	void libros_sinDisponibles() throws Exception {
		// Entrada: 5 total, 3 prestados, 1 reservado, 1 mantenimiento
		// Esperado: 0 disponibles
		mockMvc.perform(get("/api/libros/disponibles/5/3/1/1")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("0")));
	}

// 5. Puntaje del usuario

	@Test
	void puntaje_usuarioExcelente() throws Exception {
		// Entrada: 20 libros, 20 entregas a tiempo, 0 retrasos, 0 multas
		// Esperado: respuesta exitosa con puntaje alto
		mockMvc.perform(get("/api/usuario/puntaje/20/20/0/0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void puntaje_usuarioPromedio() throws Exception {
		// Entrada: 10 libros, 8 entregas a tiempo, 2 retrasos, 1 multa pagada
		// Esperado: respuesta exitosa
		mockMvc.perform(get("/api/usuario/puntaje/10/8/2/1")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void puntaje_usuarioConMuchasMultas() throws Exception {
		// Entrada: 5 libros, 2 entregas a tiempo, 5 retrasos, 4 multas
		// Esperado: respuesta exitosa con puntaje bajo
		mockMvc.perform(get("/api/usuario/puntaje/5/2/5/4")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

// 6. Días de devolución 

	@Test
	void devolucion_estudianteSinRetrasos() throws Exception {
		// Entrada: 7 días base, 3 días extra, ESTUDIANTE, 0 retrasos previos
		// Esperado: respuesta exitosa
		mockMvc.perform(get("/api/prestamo/dias-devolucion/7/3/ESTUDIANTE/0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void devolucion_docenteConBeneficios() throws Exception {
		// Entrada: 14 días base, 7 días extra, DOCENTE, 0 retrasos previos
		// Esperado: respuesta exitosa (docentes tienen más días)
		mockMvc.perform(get("/api/prestamo/dias-devolucion/14/7/DOCENTE/0")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

	@Test
	void devolucion_estudianteConRetrasos() throws Exception {
		// Entrada: 7 días base, 0 extra, ESTUDIANTE, 3 retrasos previos (penalización)
		// Esperado: respuesta exitosa con días reducidos
		mockMvc.perform(get("/api/prestamo/dias-devolucion/7/0/ESTUDIANTE/3")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("La respuesta es")));
	}

// 7. Progreso de lectura

	@Test
	void progreso_mitadDelLibro() throws Exception {
		// Entrada: 150 páginas leídas de 300 total, 5 días transcurridos de 10
		// planeados
		// Esperado: 50% de progreso
		mockMvc.perform(get("/api/lectura/progreso/150/300/5/10")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("50.0")));
	}

	@Test
	void progreso_libroTerminado() throws Exception {
		// Entrada: 300 páginas leídas de 300 total
		// Esperado: 100% de progreso
		mockMvc.perform(get("/api/lectura/progreso/300/300/10/10")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("100.0")));
	}

	@Test
	void progreso_inicioLectura() throws Exception {
		// Entrada: 30 páginas leídas de 300 total
		// Esperado: 10% de progreso
		mockMvc.perform(get("/api/lectura/progreso/30/300/1/10")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("10.0")));
	}

// 8. Uso de la biblioteca

	@Test
	void uso_bibliotecaAltaDemanda() throws Exception {
		// Entrada: 80 usuarios activos de 100, 160 libros prestados de 200
		// Esperado: 80% de uso de libros
		mockMvc.perform(get("/api/biblioteca/uso/80/160/100/200")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("80.0")));
	}

	@Test
	void uso_bibliotecaMitadCapacidad() throws Exception {
		// Entrada: 50 usuarios de 100, 120 libros de 200
		// Esperado: 60% de uso de libros
		mockMvc.perform(get("/api/biblioteca/uso/50/120/100/200")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("60.0")));
	}

	@Test
	void uso_bibliotecaBajaDemanda() throws Exception {
		// Entrada: 10 usuarios de 100, 20 libros de 200
		// Esperado: 10% de uso de libros
		mockMvc.perform(get("/api/biblioteca/uso/10/20/100/200")).andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("10.0")));
	}
}