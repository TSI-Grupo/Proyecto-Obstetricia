package com.obstetricia.segurity.obstetricia.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.obstetricia.segurity.obstetricia.modelo.EstadoPerfil;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.servicio.PacienteServicio;
import com.obstetricia.segurity.obstetricia.servicio.PerfilDinamicoServicio;




@Controller
public class RegistroControlador {


    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private PerfilDinamicoServicio perfilDinamicoServicio;

    @GetMapping("/login")
    public String iniciarSesion(){
        return "login";
    }
    @GetMapping("/")
    public String verPaginaDeInicio(@RequestParam(defaultValue = "0") int page, Model model) {
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
