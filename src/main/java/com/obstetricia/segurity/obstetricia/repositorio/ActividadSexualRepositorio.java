package com.obstetricia.segurity.obstetricia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obstetricia.segurity.obstetricia.modelo.ActividadSexual;


@Repository
public interface ActividadSexualRepositorio extends JpaRepository<ActividadSexual, Long> {
    List<ActividadSexual> findByPacienteId(Long pacienteId);
}

