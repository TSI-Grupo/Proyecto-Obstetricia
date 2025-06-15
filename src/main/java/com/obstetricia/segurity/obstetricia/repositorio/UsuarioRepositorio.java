package com.obstetricia.segurity.obstetricia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obstetricia.segurity.obstetricia.modelo.Usuario;

@Repository
public interface UsuarioRepositorio extends  JpaRepository<Usuario,Long> {

    public Usuario findByEmail(String email);
}
