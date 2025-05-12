package com.obstetricia.segurity.obstetricia.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.obstetricia.segurity.obstetricia.modelo.Usuario;
import com.obstetricia.segurity.obstetricia.servicio.UsuarioServicio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
@RequestMapping("/usuarios")
public class UsuarioCRUDControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;


    // Guardar usuario (POST desde formulario de crear)
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioServicio.crearUsuario(usuario);
        return "redirect:/usuarios";  // redirige al listado
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/editar";
    }

    // Actualizar usuario (POST desde formulario de editar)
    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioServicio.actualizarUsuario(usuario);
        return "redirect:/usuarios";  // redirige al listado
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/usuarios";  // redirige al listado
    }

    // Listar usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.obtenerTodosLosUsuarios());
        return "administracionEsp";  
    }
}
