package pe.edu.uni.smartlibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "Prueba 1";
    }
    
    @GetMapping("/laura")
    public String Jesus2() {
        return "Prueba 2";
    }
}