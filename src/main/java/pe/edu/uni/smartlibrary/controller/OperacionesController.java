package pe.edu.uni.smartlibrary.controller;

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
    public String calcularMulta(
            @PathVariable(name = "diasRetraso") int diasRetraso,
            @PathVariable(name = "costoPorDia") double costoPorDia,
            @PathVariable(name = "diasGracia") int diasGracia,
            @PathVariable(name = "tipoUsuario") String tipoUsuario) {

        int diasConMulta = diasRetraso - diasGracia;

        if (diasConMulta < 0) {
            diasConMulta = 0;
        }

        double total = diasConMulta * costoPorDia;

        if ("DOCENTE".equalsIgnoreCase(tipoUsuario)) {
            total = total / 2;
        }

        return String.valueOf(total);
    }

    // 2. Calcular días necesarios para leer un libro
    @GetMapping("/lectura/dias/{totalPaginas}/{paginasPorDia}/{dificultad}/{horasDisponibles}")
    public String calcularDiasLectura(
            @PathVariable(name = "totalPaginas") int totalPaginas,
            @PathVariable(name = "paginasPorDia") int paginasPorDia,
            @PathVariable(name = "dificultad") int dificultad,
            @PathVariable(name = "horasDisponibles") double horasDisponibles) {

        double dias = (double) totalPaginas / paginasPorDia;
        return "<h3>La respuesta es: </h3>"+ String.valueOf(dias);
    }

    // 3. Calcular libros que puede leer en un mes
    @GetMapping("/lectura/libros-mes/{paginasPorLibro}/{paginasPorDia}/{diasLecturaMes}/{nivelConcentracion}")
    public String calcularLibrosMes(
            @PathVariable(name = "paginasPorLibro") int paginasPorLibro,
            @PathVariable(name = "paginasPorDia") int paginasPorDia,
            @PathVariable(name = "diasLecturaMes") int diasLecturaMes,
            @PathVariable(name = "nivelConcentracion") double nivelConcentracion) {

        double libros = (double) (paginasPorDia * diasLecturaMes) / paginasPorLibro;
        return "<h3>La respuesta es: </h3>"+ String.valueOf(libros);
    }

    // 4. Calcular libros disponibles
    @GetMapping("/libros/disponibles/{totalEjemplares}/{prestados}/{reservados}/{enMantenimiento}")
    public String calcularLibrosDisponibles(
            @PathVariable(name = "totalEjemplares") int totalEjemplares,
            @PathVariable(name = "prestados") int prestados,
            @PathVariable(name = "reservados") int reservados,
            @PathVariable(name = "enMantenimiento") int enMantenimiento) {

        int disponibles = totalEjemplares - prestados - reservados - enMantenimiento;
        return "<h3>La respuesta es: </h3>"+ String.valueOf(disponibles);
    }

    // 5. Calcular puntaje del usuario
    @GetMapping("/usuario/puntaje/{librosLeidos}/{entregasATiempo}/{retrasos}/{multasPagadas}")
    public String calcularPuntajeUsuario(
            @PathVariable(name = "librosLeidos") int librosLeidos,
            @PathVariable(name = "entregasATiempo") int entregasATiempo,
            @PathVariable(name = "retrasos") int retrasos,
            @PathVariable(name = "multasPagadas") int multasPagadas) {

        int puntaje = librosLeidos - retrasos;
        return "<h3>La respuesta es: </h3>"+ String.valueOf(puntaje);
    }

    // 6. Calcular días de devolución
    @GetMapping("/prestamo/dias-devolucion/{diasBase}/{diasExtra}/{tipoUsuario}/{retrasosPrevios}")
    public String calcularDiasDevolucion(
            @PathVariable(name = "diasBase") int diasBase,
            @PathVariable(name = "diasExtra") int diasExtra,
            @PathVariable(name = "tipoUsuario") String tipoUsuario,
            @PathVariable(name = "retrasosPrevios") int retrasosPrevios) {

        int dias = diasBase + diasExtra;
        return "<h3>La respuesta es: </h3>"+ String.valueOf(dias);
    }

    // 7. Calcular progreso de lectura
    @GetMapping("/lectura/progreso/{paginasLeidas}/{totalPaginas}/{diasTranscurridos}/{diasPlaneados}")
    public String calcularProgresoLectura(
            @PathVariable(name = "paginasLeidas") int paginasLeidas,
            @PathVariable(name = "totalPaginas") int totalPaginas,
            @PathVariable(name = "diasTranscurridos") int diasTranscurridos,
            @PathVariable(name = "diasPlaneados") int diasPlaneados) {

        double progreso = (double) (paginasLeidas * 100) / totalPaginas;
        return "<h3>La respuesta es: </h3>"+ String.valueOf(progreso);
    }

    // 8. Calcular nivel de uso de la biblioteca
    @GetMapping("/biblioteca/uso/{usuariosActivos}/{librosPrestados}/{capacidadUsuarios}/{capacidadLibros}")
    public String calcularUsoBiblioteca(
            @PathVariable(name = "usuariosActivos") int usuariosActivos,
            @PathVariable(name = "librosPrestados") int librosPrestados,
            @PathVariable(name = "capacidadUsuarios") int capacidadUsuarios,
            @PathVariable(name = "capacidadLibros") int capacidadLibros) {

        double uso = (double) (librosPrestados * 100) / capacidadLibros;
        return "<h3>La respuesta es: </h3>"+ String.valueOf(uso);
    }
}
