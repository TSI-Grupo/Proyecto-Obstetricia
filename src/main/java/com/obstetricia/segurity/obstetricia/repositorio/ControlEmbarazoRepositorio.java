package com.obstetricia.segurity.obstetricia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.obstetricia.segurity.obstetricia.modelo.ControlEmbarazo;

public interface ControlEmbarazoRepositorio extends JpaRepository<ControlEmbarazo, Long> {

        List<ControlEmbarazo> findByPacienteId(Long pacienteId);

}