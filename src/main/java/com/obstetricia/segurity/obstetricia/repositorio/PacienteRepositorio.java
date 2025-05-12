package com.obstetricia.segurity.obstetricia.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;

public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByRut(String rut);
}

