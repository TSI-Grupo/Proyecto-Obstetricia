package com.obstetricia.segurity.obstetricia.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.obstetricia.segurity.obstetricia.modelo.IngestaAnticonceptiva;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.repositorio.IngestaAnticonceptivaRepositorio;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;
import com.obstetricia.segurity.obstetricia.servicio.IngestaAnticonceptivaServicio;
import com.obstetricia.segurity.obstetricia.servicio.PacienteServicio;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;






@Controller
@RequestMapping("/anticonceptivos")
public class IngestaAnticonceptivaControlador {

    @Autowired
    private IngestaAnticonceptivaServicio servicio;

    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private IngestaAnticonceptivaRepositorio repositorio;

    // Formulario inicial para validar RUT
    @GetMapping ("/validar-rut")
    public String mostrarFormularioRut(Model model) {
        model.addAttribute("rutPaciente", "");
        return "validarRutAnticonceptivoForm";
    }

    // Procesamiento de RUT
    @PostMapping ("/validar-rut")
    public String procesarRut(@RequestParam String rutPaciente, Model model) {
        Optional<Paciente> pacienteOpt = pacienteServicio.buscarPorRut(rutPaciente);

        if (pacienteOpt.isPresent()) {
            IngestaAnticonceptiva ingesta = new IngestaAnticonceptiva();
            ingesta.setPaciente(pacienteOpt.get());

            // Rango de fechas
            LocalDate maxDate = LocalDate.now();
            LocalDate minDate = maxDate.minusYears(1);

            model.addAttribute("ingesta", ingesta);
            model.addAttribute("minDate", minDate);
            model.addAttribute("maxDate", maxDate);
            model.addAttribute("marcas", obtenerMarcasDisponibles());
            model.addAttribute("efectosAdversos", List.of(
                "Náuseas", 
                "Dolor de cabeza", 
                "Aumento de peso", 
                "Sangrado intermenstrual"
            ));
            model.addAttribute("accionesOlvido", List.of(
                "Tomar la pastilla olvidada lo antes posible", 
                "Consultar con un profesional de salud", 
                "Usar protección adicional por 7 días"
            ));

            return "ingestaForm";
        }   else {
            model.addAttribute("error", "El paciente con RUT " + rutPaciente + " no existe.");
            return "validarRutAnticonceptivoForm";
        }
    }

    // Guardado de la ingesta
    @PostMapping("/guardar")
    public String guardarIngesta(
        @ModelAttribute("ingesta") IngestaAnticonceptiva ingesta,
        @RequestParam("rutPaciente") String rutPaciente,
        @RequestParam(name = "marca", required = false) String marcaSeleccionada,
        @RequestParam(name = "otraMarca", required = false) String otraMarca,
        BindingResult result,
        Model model,
        HttpServletRequest request) {

        Optional<Paciente> pacienteOpt = pacienteServicio.buscarPorRut(rutPaciente);

        if (pacienteOpt.isEmpty()) {
            result.reject("paciente", "Paciente no encontrado para el RUT: " + rutPaciente);
            model.addAttribute("ingesta", ingesta);
            return "ingestaForm";
        }

        ingesta.setPaciente(pacienteOpt.get());

        // Si NO se olvidó la pastilla, procesamos la marca
        if (!ingesta.isOlvidada()) {
        if ("Otro".equals(marcaSeleccionada) && otraMarca != null && !otraMarca.trim().isEmpty()) {
            ingesta.setMarca(otraMarca.trim());
        } else {
            ingesta.setMarca(marcaSeleccionada); // marca normal
            }
        } else {
            // Si se olvidó la pastilla, limpiamos la marca
            ingesta.setMarca(null);
        }

        String otraMarcaIngresada = request.getParameter("otraMarca");

        if ("Otro".equals(marcaSeleccionada) && otraMarcaIngresada != null && !otraMarcaIngresada.isBlank()) {
            ingesta.setMarca(otraMarcaIngresada.trim());
        }

        // Guardar
        if (ingesta.getId() != null && repositorio.existsById(ingesta.getId())) {
            IngestaAnticonceptiva existente = repositorio.findById(ingesta.getId()).get();
            existente.setFecha(ingesta.getFecha());
            existente.setOlvidada(ingesta.isOlvidada());
            existente.setEfectosAdversos(ingesta.getEfectosAdversos());
            existente.setMarca(ingesta.getMarca());
            existente.setPaciente(ingesta.getPaciente());
            servicio.guardarIngesta(existente);
        } else {
            servicio.guardarIngesta(ingesta);
        }

        model.addAttribute("mensaje", generarSugerencia(ingesta));
        model.addAttribute("ingestas", servicio.listarPorPaciente(pacienteOpt.get().getId()));
        return "ingestaListado";
    }

    @GetMapping("/listado")
    public String listarTodasLasIngestas(Model model) {
        model.addAttribute("ingestas", servicio.listarTodas());
        return "ingestaListado";
    }

    private String generarSugerencia(IngestaAnticonceptiva ingesta) {
        if (ingesta.isOlvidada()) {
            return "La paciente ha olvidado la pastilla. Se recomienda la consulta con un médico especialista y considerar tomar la pastilla olvidada lo antes posible.";
        }
        if (ingesta.getEfectosAdversos() != null && !ingesta.getEfectosAdversos().isBlank()) {
            return "Se han reportado efectos adversos. Se recomienda la consulta con un médico especialista.";
        }
        return "Registro guardado correctamente.";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<IngestaAnticonceptiva> ingestaOpt = repositorio.findById(id);
        if (ingestaOpt.isPresent()) {
            model.addAttribute("ingesta", ingestaOpt.get());

            model.addAttribute("marcas", obtenerMarcasDisponibles());

            String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            model.addAttribute("fechaHoy", fechaHoy);

            return "ingestaForm";
        } else {
            // Redirige al listado general si no se encuentra la ingesta
            return "redirect:/anticonceptivos/listado";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminarPorId(id);  // Este método debe estar en tu servicio
        return "redirect:/anticonceptivos/listado";
    }

    @GetMapping("/ingestas/paciente/{id}")
    public String listarEmbarazosPorPaciente(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteRepositorio.findById(id).orElse(null);
        List<IngestaAnticonceptiva> ingestas = repositorio.findByPacienteId(id);

        model.addAttribute("paciente", paciente);
        model.addAttribute("ingestas", ingestas);

        return "ingesta_paciente"; // asegúrate de tener ingesta_paciente.html en templates
    }

    private List<String> obtenerMarcasDisponibles() {
        return repositorio.findDistinctMarcas(); // Este método lo definiremos en el repositorio
    }

}