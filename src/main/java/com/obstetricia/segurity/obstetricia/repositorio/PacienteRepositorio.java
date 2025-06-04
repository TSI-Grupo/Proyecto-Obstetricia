package com.obstetricia.segurity.obstetricia.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.obstetricia.segurity.obstetricia.modelo.Paciente;

public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByRut(String rut);
    Page<Paciente> findBynombreCompletoContainingIgnoreCase(String nombreCompleto, Pageable pageable);
}

