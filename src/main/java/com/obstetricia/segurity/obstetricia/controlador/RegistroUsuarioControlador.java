package com.obstetricia.segurity.obstetricia.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registrarCuentaDeUsuario(@ModelAttribute ("usuario") UsuarioRegistroDTO registroDTO, RedirectAttributes redirectAttributes) {
      
      try {
        usuarioServicio.save(registroDTO);
        redirectAttributes.addAttribute("exito", true); // Si todo va bien
        return "redirect:/registro";
    } catch (RuntimeException e) {
        redirectAttributes.addAttribute("error", true); // Si hay error (correo ya existe)
        return "redirect:/registro";
    }
    }
    
    
}
