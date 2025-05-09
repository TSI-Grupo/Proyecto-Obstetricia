package com.obstetricia.segurity.obstetricia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obstetricia.segurity.obstetricia.modelo.Rol;

import java.util.Optional;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
