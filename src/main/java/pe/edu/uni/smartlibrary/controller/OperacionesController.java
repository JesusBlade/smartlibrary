package pe.edu.uni.smartlibrary.controller;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class OperacionesController {

    @GetMapping("/test")
    public String inicio() {
        return "API funcionando correctamente";
    }

    // 1. Calcular multa por retraso
    @GetMapping("/multa/calcular/{diasRetraso}/{costoPorDia}/{diasGracia}/{tipoUsuario}")
    public ResponseEntity<Object> calcularMulta(
            @PathVariable("diasRetraso") int diasRetraso,
            @PathVariable("costoPorDia") double costoPorDia,
            @PathVariable("diasGracia") int diasGracia,
            @PathVariable("tipoUsuario") String tipoUsuario) {

        if (diasRetraso < 0 || costoPorDia < 0) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }

        int diasConMulta = diasRetraso - diasGracia;
        if (diasConMulta < 0) diasConMulta = 0;

        double total = diasConMulta * costoPorDia;

        if ("DOCENTE".equalsIgnoreCase(tipoUsuario)) {
            total = total / 2;
        }

        return ResponseEntity.ok("La respuesta es: " + total);
    }
    
    // 2. Calcular días lectura
    @GetMapping("/lectura/dias/{totalPaginas}/{paginasPorDia}/{dificultad}/{horasDisponibles}")
    public ResponseEntity<Object> calcularDiasLectura(
            @PathVariable("totalPaginas") int totalPaginas,
            @PathVariable("paginasPorDia") int paginasPorDia,
            @PathVariable("dificultad") int dificultad,
            @PathVariable("horasDisponibles") double horasDisponibles) {

        if (totalPaginas <= 0 || paginasPorDia <= 0) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }

        double dias = (double) totalPaginas / paginasPorDia;

        String resultado = "La respuesta es:" + dias;
        return ResponseEntity.ok(resultado);
    }

    // 3. Libros por mes
    @GetMapping("/lectura/libros-mes/{paginasPorLibro}/{paginasPorDia}/{diasLecturaMes}/{nivelConcentracion}")
    public ResponseEntity<Object> calcularLibrosMes(
            @PathVariable("paginasPorLibro") int paginasPorLibro,
            @PathVariable("paginasPorDia") int paginasPorDia,
            @PathVariable("diasLecturaMes") int diasLecturaMes,
            @PathVariable("nivelConcentracion") double nivelConcentracion) {

        if (paginasPorLibro <= 0 || paginasPorDia <= 0 || diasLecturaMes <= 0) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }

        double libros = (double) (paginasPorDia * diasLecturaMes) / paginasPorLibro;

        String resultado = "La respuesta es:" + libros + ", concentración: " + nivelConcentracion;
        return ResponseEntity.ok(resultado);
    }

    // 4. Libros disponibles
    @GetMapping("/libros/disponibles/{totalEjemplares}/{prestados}/{reservados}/{enMantenimiento}")
    public ResponseEntity<Object> calcularLibrosDisponibles(
            @PathVariable ("totalEjemplares") int totalEjemplares,
            @PathVariable ("prestados") int prestados,
            @PathVariable ("reservados") int reservados,
            @PathVariable ("enMantenimiento") int enMantenimiento) {

        if (totalEjemplares < 0 || prestados < 0 || reservados < 0 || enMantenimiento < 0) {
            return ResponseEntity.badRequest().body("Datos inválidos");
        }

        if (prestados + reservados + enMantenimiento > totalEjemplares) {
            return ResponseEntity.badRequest().body("Valores exceden total");
        }

        int disponibles = totalEjemplares - prestados - reservados - enMantenimiento;

        String resultado = "La respuesta es:" + disponibles;
        return ResponseEntity.ok(resultado);
    }

    // 5. Puntaje usuario
    @GetMapping("/usuario/puntaje/{librosLeidos}/{entregasATiempo}/{retrasos}/{multasPagadas}")
    public ResponseEntity<Object> calcularPuntajeUsuario(
            @PathVariable ("librosLeidos") int librosLeidos,
            @PathVariable ("entregasATiempo") int entregasATiempo,
            @PathVariable ("retrasos") int retrasos,
            @PathVariable ("multasPagadas") int multasPagadas) {

        int puntaje = (librosLeidos * 2) + entregasATiempo - (retrasos * 2) - multasPagadas;

        String resultado = "La respuesta es: " + puntaje;
        return ResponseEntity.ok(resultado);
    }

    // 6. Días devolución
    @GetMapping("/prestamo/dias-devolucion/{diasBase}/{diasExtra}/{tipoUsuario}/{retrasosPrevios}")
    public ResponseEntity<Object> calcularDiasDevolucion(
            @PathVariable ("diasBase") int diasBase,
            @PathVariable ("diasExtra") int diasExtra,
            @PathVariable ("tipoUsuario") String tipoUsuario,
            @PathVariable ("retrasosPrevios") int retrasosPrevios) {

        int dias = diasBase + diasExtra;

        if (tipoUsuario.equalsIgnoreCase("DOCENTE")) dias += 2;
        if (retrasosPrevios > 3) dias -= 2;
        if (dias < 1) dias = 1;

        String resultado = "La respuesta es: " + dias;
        return ResponseEntity.ok(resultado);
    }

    // 7. Progreso lectura
    @GetMapping("/lectura/progreso/{paginasLeidas}/{totalPaginas}/{diasTranscurridos}/{diasPlaneados}")
    public ResponseEntity<Object> calcularProgresoLectura(
            @PathVariable ("paginasLeidas") int paginasLeidas,
            @PathVariable ("totalPaginas") int totalPaginas,
            @PathVariable ("diasTranscurridos") int diasTranscurridos,
            @PathVariable ("diasPlaneados") int diasPlaneados) {

        double progreso = (double) (paginasLeidas * 100) / totalPaginas;

        String resultado = "La respuesta es:" + progreso + "%";
        return ResponseEntity.ok(resultado);
    }

    // 8. Uso biblioteca
    @GetMapping("/biblioteca/uso/{usuariosActivos}/{librosPrestados}/{capacidadUsuarios}/{capacidadLibros}")
    public ResponseEntity<Object> calcularUsoBiblioteca(
            @PathVariable ("usuariosActivos") int usuariosActivos,
            @PathVariable ("librosPrestados") int librosPrestados,
            @PathVariable ("capacidadUsuarios") int capacidadUsuarios,
            @PathVariable ("capacidadLibros") int capacidadLibros) {

        double uso = (double) (librosPrestados * 100) / capacidadLibros;

        String resultado = "La respuesta es:" + uso + "%";
        return ResponseEntity.ok(resultado);
    }
}
