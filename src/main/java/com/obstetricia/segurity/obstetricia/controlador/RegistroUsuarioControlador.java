package com.obstetricia.segurity.obstetricia.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.obstetricia.segurity.obstetricia.dto.UsuarioRegistroDTO;
import com.obstetricia.segurity.obstetricia.servicio.UsuarioServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/registro")

public class RegistroUsuarioControlador {

    private UsuarioServicio usuarioServicio;

    public RegistroUsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO(){
        return new UsuarioRegistroDTO();
    }

    @GetMapping
    public String mostrarFormularioDeRegistro() {
      return "registro";
    } 

    @PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute ("usuario") UsuarioRegistroDTO registroDTO) {
      
      usuarioServicio.save(registroDTO);
      return "redirect:/registro?exito";
    }
    
    
}
