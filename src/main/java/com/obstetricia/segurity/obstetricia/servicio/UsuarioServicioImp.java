package com.obstetricia.segurity.obstetricia.servicio;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.dto.UsuarioRegistroDTO;
import com.obstetricia.segurity.obstetricia.modelo.Rol;
import com.obstetricia.segurity.obstetricia.modelo.Usuario;
import com.obstetricia.segurity.obstetricia.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImp implements UsuarioServicio{
    
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicioImp(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario save(UsuarioRegistroDTO registroDTO) {
        if (usuarioRepositorio.findByEmail(registroDTO.getEmail()) != null) {
            throw new RuntimeException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario(registroDTO.getNombre(),registroDTO.getApellido(),registroDTO.getEmail(),passwordEncoder.encode(registroDTO.getPassword()),
                          Arrays.asList(new Rol("ROLE_USER")));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesARoles(usuario.getRoles()));
    }
    private Collection <? extends GrantedAuthority> mapearAutoridadesARoles(Collection <Rol> roles){
         return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }
    
    @Override
    public List<Usuario> listaUsuarios() {
        return usuarioRepositorio.findAll();
    }
}
