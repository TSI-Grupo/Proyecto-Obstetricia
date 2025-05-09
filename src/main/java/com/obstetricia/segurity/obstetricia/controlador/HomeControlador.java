package com.obstetricia.segurity.obstetricia.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.obstetricia.segurity.obstetricia.modelo.Usuario;
import com.obstetricia.segurity.obstetricia.servicio.UsuarioServicio;


import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/administracionEsp")
    public String mostrarInicio(Model model) {
        List<Usuario> usuarios = usuarioServicio.listaUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "administracionEsp"; // esto carga tu inicio.html (la vista con la tabla)
    }
}
