package com.obstetricia.segurity.obstetricia.controlador;

import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;
import com.obstetricia.segurity.obstetricia.servicio.CicloMenstrualServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/ciclos_menstruales")
public class CicloMenstrualControlador {

    @Autowired
    private CicloMenstrualServicio servicio;

    @GetMapping("/formulario_ciclo")
    public String mostrarFormulario() {
        return "formulario_ciclo"; // Este nombre debe coincidir con el archivo formulario_ciclo.html
    }

    @PostMapping("/registrar")
    public String registrarCiclo(CicloMenstrual ciclo, Model model) {
        servicio.registrarCiclo(ciclo);
        model.addAttribute("mensaje", "Ciclo registrado exitosamente.");
        return "redirect:/ciclos_menstruales/formulario_ciclo"; // Asegúrate de redirigir correctamente
    }
}
