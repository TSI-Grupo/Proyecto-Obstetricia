package com.obstetricia.segurity.obstetricia.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;
import com.obstetricia.segurity.obstetricia.servicio.PacienteServicio;

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

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("paciente", new Paciente());
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
    model.addAttribute("pacientes", pacientes);
    return "historial";
}

}
