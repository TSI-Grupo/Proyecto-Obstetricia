package com.obstetricia.segurity.obstetricia.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.obstetricia.segurity.obstetricia.modelo.Rol;
import com.obstetricia.segurity.obstetricia.modelo.Usuario;
import com.obstetricia.segurity.obstetricia.repositorio.RolRepositorio;
import com.obstetricia.segurity.obstetricia.repositorio.UsuarioRepositorio;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        inicializarDatos();
    }
    
    @Transactional
    public void inicializarDatos() {
        // Verificar si existe el rol ADMIN
        Rol adminRol = rolRepositorio.findByNombre("ROLE_ADMIN")
                .orElseGet(() -> {
                    Rol rol = new Rol();
                    rol.setNombre("ROLE_ADMIN");
                    return rolRepositorio.save(rol);
                });

        // Verificar si existe el usuario admin
        Usuario usuarioExistente = usuarioRepositorio.findByEmail("admin@admin.com");
        
        if (usuarioExistente == null) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // CONTRASEÑA DEFAULT
            
            // Crear un nuevo conjunto y agregar el rol
            Set<Rol> roles = new HashSet<>();
            roles.add(adminRol);
            admin.setRoles(roles);
            
            usuarioRepositorio.save(admin);
            System.out.println("Usuario administrador creado con éxito");
        } else {
            System.out.println("El usuario administrador ya existe");
        }
    }
}