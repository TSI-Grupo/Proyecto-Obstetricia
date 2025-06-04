package com.obstetricia.segurity.obstetricia.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obstetricia.segurity.obstetricia.modelo.ActividadSexual;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.repositorio.ActividadSexualRepositorio;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;
import com.obstetricia.segurity.obstetricia.servicio.ActividadSexualServicio;
import com.obstetricia.segurity.obstetricia.servicio.PacienteServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/actividad-sexual")
public class ActividadSexualControlador {

    @Autowired
    private PacienteServicio pacienteService;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private ActividadSexualServicio actividadSexualService;

    @Autowired
    private ActividadSexualRepositorio actividadSexualRepositorio;

    // Paso 1: Mostrar formulario para ingresar RUT
    @GetMapping("/validar-rut")
    public String mostrarFormularioRut(Model model) {
        model.addAttribute("rutPaciente", "");
        return "validarRutForm";
    }

    @GetMapping("/listado")
    public String mostrarListadoActividades(Model model) {
    model.addAttribute("actividadesSexuales", actividadSexualService.obtenerTodas());
    return "actividadSexualListado";
}

    // Paso 2: Procesar RUT
    @PostMapping("/validar-rut")
    public String procesarRut(@RequestParam String rutPaciente, Model model) {
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorRut(rutPaciente);

        if (pacienteOpt.isPresent()) {
            ActividadSexual actividadSexual = new ActividadSexual();
            actividadSexual.setPaciente(pacienteOpt.get());
            model.addAttribute("actividadSexual", actividadSexual);
            return "actividadSexualForm";
        } else {
            model.addAttribute("error", "El paciente con RUT " + rutPaciente + " no existe.");
            return "validarRutForm";
        }
    }

    // Paso 3: Guardar actividad
    @PostMapping("/guardar")
    public String guardarRegistro(@ModelAttribute ActividadSexual actividadSexual, Model model) {
        actividadSexualService.guardar(actividadSexual);

        if (actividadSexual.getUsoProteccion() != null && !actividadSexual.getUsoProteccion()) {
            model.addAttribute("sugerencia", "Recuerda que el preservativo es el único método que protege de ITS.");
        }
        return "actividadSexualResultado";
    }

        // NUEVO: Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<ActividadSexual> actividadOpt = actividadSexualService.obtenerPorId(id);
        if (actividadOpt.isPresent()) {
            model.addAttribute("actividadSexual", actividadOpt.get());
            return "actividadSexualForm"; // reutiliza el mismo formulario
        } else {
            return "redirect:/actividad-sexual/listado";
        }
    }

    // NUEVO: Eliminar actividad
    @GetMapping("/eliminar/{id}")
    public String eliminarActividad(@PathVariable Long id) {
        actividadSexualService.eliminarPorId(id);
        return "redirect:/actividad-sexual/listado";
    }

    @GetMapping("/paciente/{id}")
    public String listarActividadSexualPorPaciente(@PathVariable Long id, Model model) {
    Paciente paciente = pacienteRepositorio.findById(id).orElse(null);
    List<ActividadSexual> actividades = actividadSexualRepositorio.findByPacienteId(id);

    model.addAttribute("paciente", paciente);
    model.addAttribute("actividades", actividades);

    return "actividad_sexual_paciente";
}

}

