package com.obstetricia.segurity.obstetricia.controlador;

import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.repositorio.CicloMenstrualRepositorio;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;
import com.obstetricia.segurity.obstetricia.servicio.CicloMenstrualServicio;
import com.obstetricia.segurity.obstetricia.servicio.PacienteServicio;

import javax.validation.Valid; // ← Cambiado de jakarta.validation a javax.validation

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ciclos-menstruales")
public class CicloMenstrualControlador {
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private PacienteServicio pacienteService;

    @Autowired
    private CicloMenstrualServicio servicio;

    @Autowired
    private CicloMenstrualRepositorio repositorio;

    // Paso 1: Mostrar formulario de validación RUT
    @GetMapping("/validar-rut")
    public String mostrarFormularioRut(Model model) {
        model.addAttribute("rutPaciente", "");
        return "validarRutCicloForm";
    }

    // Paso 2: Procesar RUT y mostrar formulario de ingreso
    @PostMapping("/validar-rut")
    public String procesarRut(@RequestParam String rutPaciente, Model model) {
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorRut(rutPaciente);

        if (pacienteOpt.isPresent()) {
            CicloMenstrual ciclo = new CicloMenstrual();
            ciclo.setPaciente(pacienteOpt.get());
            model.addAttribute("ciclo", ciclo);
            return "formulario_ciclo";
        } else {
            model.addAttribute("error", "El paciente con RUT " + rutPaciente + " no existe.");
            return "validarRutCicloForm";
        }
    }

    // Paso 3: Guardar ciclo
    @PostMapping("/guardar")
    public String guardarCiclo(@ModelAttribute @Valid CicloMenstrual ciclo, BindingResult result, Model model) {
        LocalDate hoy = LocalDate.now();

        if (ciclo.getPrimerDiaPeriodo() != null && ciclo.getPrimerDiaPeriodo().isAfter(hoy)) {
            result.rejectValue("primerDiaPeriodo", "error.ciclo", "La fecha no puede ser futura.");
        }

        if (ciclo.getDuracionCiclo() != null && ciclo.getPrimerDiaPeriodo() != null) {
            long diasTranscurridos = ChronoUnit.DAYS.between(ciclo.getPrimerDiaPeriodo(), hoy) + 1;

            if (ciclo.getDuracionCiclo() < 1 || ciclo.getDuracionCiclo() > diasTranscurridos) {
                result.rejectValue("duracionCiclo", "error.ciclo",
                    "La duración debe estar entre 1 y " + diasTranscurridos + " días.");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("ciclo", ciclo);
            return "formulario_ciclo";
        }

        if (ciclo.getId() != null && repositorio.existsById(ciclo.getId())) {
            CicloMenstrual existente = repositorio.findById(ciclo.getId()).get();
            existente.setPrimerDiaPeriodo(ciclo.getPrimerDiaPeriodo());
            existente.setDuracionCiclo(ciclo.getDuracionCiclo());
            repositorio.save(existente);

        } else {
            servicio.registrarCiclo(ciclo);
        }

        return "cicloMenstrualResultado";
    }

    // Paso 4: Listado
    @GetMapping("/listado")
    public String mostrarListado(Model model) {
        List<CicloMenstrual> lista = servicio.obtenerTodos();

        List<CicloMenstrual> ciclosFormateados = lista.stream().map(ciclo -> {
            String sintomasFormateados = ciclo.getSintomas() != null
                ? String.join("<br/>", ciclo.getSintomas().split(","))
                : "";
            String estadoAnimoFormateado = ciclo.getEstadoAnimo() != null
                ? String.join("<br/>", ciclo.getEstadoAnimo().split(","))
                : "";

            ciclo.setSintomasFormateados(sintomasFormateados);
            ciclo.setEstadoAnimoFormateado(estadoAnimoFormateado);
            return ciclo;
        }).toList();

        model.addAttribute("ciclos", ciclosFormateados);
        return "cicloMenstrualListado";
    }

    // Paso 5: Editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        CicloMenstrual ciclo = repositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de ciclo no válido: " + id));
        model.addAttribute("ciclo", ciclo);
        return "formulario_ciclo";
    }

    // Paso 6: Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminarPorId(id);
        return "redirect:/ciclos-menstruales/listado";
    }

@GetMapping("/ciclos_paciente/{id}")
public String listarPorPaciente(@PathVariable Long id, Model model) {
    Paciente paciente = pacienteRepositorio.findById(id).orElse(null);
    List<CicloMenstrual> ciclos = repositorio.findByPacienteId(id);

    // Agregamos formateo como en el listado general
    List<CicloMenstrual> ciclosFormateados = ciclos.stream().map(ciclo -> {
        String sintomasFormateados = ciclo.getSintomas() != null
                ? String.join("<br/>", ciclo.getSintomas().split(","))
                : "";

        String estadoAnimoFormateado = ciclo.getEstadoAnimo() != null
                ? String.join("<br/>", ciclo.getEstadoAnimo().split(","))
                : "";

        ciclo.setSintomasFormateados(sintomasFormateados);
        ciclo.setEstadoAnimoFormateado(estadoAnimoFormateado);
        return ciclo;
    }).toList();

    model.addAttribute("paciente", paciente);
    model.addAttribute("ciclos", ciclosFormateados);

    return "ciclos_paciente";
}

}
