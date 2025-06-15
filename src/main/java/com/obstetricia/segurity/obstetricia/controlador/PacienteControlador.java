package com.obstetricia.segurity.obstetricia.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obstetricia.segurity.obstetricia.modelo.EstadoPerfil;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;
import com.obstetricia.segurity.obstetricia.servicio.PacienteServicio;
import com.obstetricia.segurity.obstetricia.servicio.PerfilDinamicoServicio;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/pacientes")
public class PacienteControlador {
    
    @Autowired
    private PacienteServicio pacienteServicio;

    @Autowired
    private PacienteRepositorio pacienteRepo;

    @Autowired
    private PerfilDinamicoServicio perfilDinamicoServicio;

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("paciente", new Paciente());

        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(65); // máximo 65 años
        LocalDate maxDate = today.minusYears(11); // mínimo 11 años

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("minNacimiento", minDate.format(formatter));
        model.addAttribute("maxNacimiento", maxDate.format(formatter));

        return "paciente_form";
    }

    @PostMapping("/save")
    public String savePaciente(@ModelAttribute Paciente paciente) {
        pacienteRepo.save(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping
    public String listPacientes(Model model) {
        model.addAttribute("pacientes", pacienteRepo.findAll());
        return "pacientes_admin";
    }

    @GetMapping("/edit/{id}")
    public String editPaciente(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteRepo.findById(id).orElseThrow();
        model.addAttribute("paciente", paciente);

        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(65); // máximo 65 años
        LocalDate maxDate = today.minusYears(11); // mínimo 11 años

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("minNacimiento", minDate.format(formatter));
        model.addAttribute("maxNacimiento", maxDate.format(formatter));
        
        return "paciente_form";
    }

    @GetMapping("/delete/{id}")
    public String deletePaciente(@PathVariable Long id) {
        pacienteRepo.deleteById(id);
        return "redirect:/pacientes";
    }

    @GetMapping("/historial")
    public String listarPacientes(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Paciente> pacientes = pacienteServicio.findAll(PageRequest.of(page, 10));

        Map<Long, EstadoPerfil> estadosPerfil = new HashMap<>();
        for (Paciente paciente : pacientes.getContent()) {
            EstadoPerfil estado = perfilDinamicoServicio.determinarPerfil(paciente.getId());
            estadosPerfil.put(paciente.getId(), estado);
        }

        model.addAttribute("pacientes", pacientes);
        model.addAttribute("estadosPerfil", estadosPerfil); // 👈 esto es nuevo

        return "historial";
    }


}
