package com.obstetricia.segurity.obstetricia.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.obstetricia.segurity.obstetricia.modelo.Rol;
import com.obstetricia.segurity.obstetricia.modelo.Usuario;
import com.obstetricia.segurity.obstetricia.repositorio.RolRepositorio;
import com.obstetricia.segurity.obstetricia.repositorio.UsuarioRepositorio;

import java.util.Collections;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si existe el rol ADMIN
        Rol adminRol = rolRepositorio.findByNombre("ROLE_ADMIN")
                .orElseGet(() -> {
                    Rol rol = new Rol();
                    rol.setNombre("ROLE_ADMIN");
                    return rolRepositorio.save(rol);
                });

        Optional<Usuario> usuarioOptional = Optional.of(usuarioRepositorio.findByEmail("admin@admin.com"));
        if (!usuarioOptional.isPresent()) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // CONTRASEÑA DEFAULT
            admin.setRoles(Collections.singleton(adminRol));
            usuarioRepositorio.save(admin);
        }
    }
}
