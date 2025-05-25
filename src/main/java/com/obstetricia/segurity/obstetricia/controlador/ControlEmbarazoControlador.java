package com.obstetricia.segurity.obstetricia.controlador;

import com.obstetricia.segurity.obstetricia.modelo.ControlEmbarazo;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.servicio.ControlEmbarazoServicio;
import com.obstetricia.segurity.obstetricia.repositorio.ControlEmbarazoRepositorio;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;
import com.obstetricia.segurity.obstetricia.servicio.PacienteServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/embarazo")
public class ControlEmbarazoControlador {

    @Autowired
    private ControlEmbarazoServicio servicio;

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ControlEmbarazoRepositorio repositorio;

    @GetMapping("/validar-rut")
    public String mostrarFormularioRut(Model model) {
        model.addAttribute("rutPaciente", "");
        return "validarRutEmbarazoForm";
    }

    @PostMapping("/validar-rut")
    public String procesarRut(@RequestParam String rutPaciente, Model model) {
        Optional<Paciente> pacienteOpt = pacienteServicio.buscarPorRut(rutPaciente);

        if (pacienteOpt.isPresent()) {
            ControlEmbarazo embarazo = new ControlEmbarazo();
            embarazo.setPaciente(pacienteOpt.get());
            model.addAttribute("controlEmbarazo", embarazo);
            return "formulario_embarazo";
        } else {
            model.addAttribute("error", "El paciente con RUT " + rutPaciente + " no existe.");
            return "validarRutEmbarazoForm";
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        ControlEmbarazo controlEmbarazo = new ControlEmbarazo();
        controlEmbarazo.setPaciente(new Paciente());
        model.addAttribute("controlEmbarazo", controlEmbarazo);
        return "formulario_embarazo";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<ControlEmbarazo> embarazoOpt = repositorio.findById(id);
        if (embarazoOpt.isPresent()) {
            model.addAttribute("controlEmbarazo", embarazoOpt.get());
            return "formulario_embarazo";
        } else {
            // Podrías manejar mejor el error, por ejemplo redirigir con mensaje flash
            return "redirect:/embarazo/embarazoListado";
        }
    }

    @PostMapping("/guardar")
    public String guardarEmbarazo(
        @ModelAttribute ControlEmbarazo controlEmbarazo,
        @RequestParam("rutPaciente") String rutPaciente,
        BindingResult result,
        Model model) {

            // Buscar paciente en base al rutPaciente
            Optional<Paciente> pacienteOpt = pacienteServicio.buscarPorRut(rutPaciente);
            if (pacienteOpt.isEmpty()) {
                // Si no existe paciente, devolver error o redirigir
                result.reject("paciente", "Paciente no encontrado para el RUT: " + rutPaciente);
                model.addAttribute("controlEmbarazo", controlEmbarazo);
                return "formulario_embarazo";
            }
            // Setear el paciente completo en controlEmbarazo
            controlEmbarazo.setPaciente(pacienteOpt.get());

            // Validaciones y guardado como antes
            if (controlEmbarazo.getUltimoDiaMenstruacion() != null && controlEmbarazo.getUltimoDiaMenstruacion().isAfter(LocalDate.now())) {
                result.rejectValue("ultimoDiaMenstruacion", "error.embarazo", "La fecha no puede ser futura.");
            }

            // Validar que no sea anterior a hace 42 semanas (294 días)
            if (controlEmbarazo.getUltimoDiaMenstruacion() != null && controlEmbarazo.getUltimoDiaMenstruacion().isBefore(LocalDate.now().minusDays(294))) {
                result.rejectValue("ultimoDiaMenstruacion", "error.embarazo", "La fecha no puede ser anterior a 42 semanas.");
            }

            if (result.hasErrors()) {
                model.addAttribute("controlEmbarazo", controlEmbarazo);
                return "formulario_embarazo";
            }

            controlEmbarazo.setSemanas(controlEmbarazo.calcularSemanas());
            controlEmbarazo.setTrimestre(controlEmbarazo.calcularTrimestre());

            if (controlEmbarazo.getId() != null && repositorio.existsById(controlEmbarazo.getId())) {
                ControlEmbarazo existente = repositorio.findById(controlEmbarazo.getId()).get();

                existente.setUltimoDiaMenstruacion(controlEmbarazo.getUltimoDiaMenstruacion());
                existente.setSemanas(controlEmbarazo.getSemanas());
                existente.setTrimestre(controlEmbarazo.getTrimestre());
                existente.setPaciente(controlEmbarazo.getPaciente());

                repositorio.save(existente);
            } else {
                repositorio.save(controlEmbarazo);
            }

            return "embarazoResultado";
        }

    @GetMapping("/embarazoListado")
    public String listarEmbarazos(Model model) {
        List<ControlEmbarazo> lista = repositorio.findAll();
        model.addAttribute("embarazos", lista);
        return "embarazoListado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminarPorId(id);
        return "redirect:/embarazo/embarazoListado";
    }
@GetMapping("/embarazos/paciente/{id}")
public String listarEmbarazosPorPaciente(@PathVariable Long id, Model model) {
    Paciente paciente = pacienteRepositorio.findById(id).orElse(null);
    List<ControlEmbarazo> embarazos = repositorio.findByPacienteId(id);

    model.addAttribute("paciente", paciente);
    model.addAttribute("embarazos", embarazos);

    return "embarazos_paciente"; // asegúrate de tener embarazos_paciente.html en templates
}

}


