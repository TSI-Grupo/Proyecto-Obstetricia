package com.obstetricia.segurity.obstetricia.controlador;

import com.obstetricia.segurity.obstetricia.dto.CicloMenstrualDTO;
import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;
import com.obstetricia.segurity.obstetricia.servicio.CicloMenstrualServicio;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ciclos_menstruales")
public class CicloMenstrualControlador {

    @Autowired
    private CicloMenstrualServicio servicio;

    @GetMapping("/formulario_ciclo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ciclo", new CicloMenstrual());
        return "formulario_ciclo";
    }

    @PostMapping("/registrar")
    public String registrarCiclo(@ModelAttribute @Valid CicloMenstrual ciclo, 
            @ModelAttribute @Valid CicloMenstrualDTO ciclodto, 
            BindingResult result, 
            Model model) {// CicloMenstrualDTO ciclodto y BindingResult result implementados
        
        LocalDate hoy = LocalDate.now();

        // Validar fecha futura implementada
        if (ciclo.getPrimerDiaPeriodo() != null && ciclo.getPrimerDiaPeriodo().isAfter(hoy)) {
            result.rejectValue("primerDia", "error.ciclo", "La fecha no puede ser futura.");
        }

        // Validar duración del ciclo vs días desde el primer día implementada
        if (ciclo.getDuracionCiclo() != null && ciclo.getPrimerDiaPeriodo() != null) {
            long diasTranscurridos = ChronoUnit.DAYS.between(ciclo.getPrimerDiaPeriodo(), hoy);
            if (diasTranscurridos > 0 && ciclo.getDuracionCiclo() > diasTranscurridos) {
                result.rejectValue("duracionCiclo", "error.ciclo", "La duración no puede ser mayor que los días desde el primer día del período (" + diasTranscurridos + " días).");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("ciclo", ciclodto);
            return "formulario_ciclo"; // Asegúrate que el HTML se llame así o ajusta este nombre
        }
        
        servicio.registrarCiclo(ciclo);
        model.addAttribute("mensaje", "Ciclo registrado exitosamente.");
        return "redirect:/ciclos_menstruales/formulario_ciclo"; // Asegúrate de redirigir correctamente
    }
}
