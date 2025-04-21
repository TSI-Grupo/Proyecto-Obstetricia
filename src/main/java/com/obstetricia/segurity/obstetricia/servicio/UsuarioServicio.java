package com.obstetricia.segurity.obstetricia.servicio;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.obstetricia.segurity.obstetricia.dto.UsuarioRegistroDTO;
import com.obstetricia.segurity.obstetricia.modelo.Usuario;

public interface UsuarioServicio extends UserDetailsService{

    public Usuario save(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listaUsuarios();

}
