package com.obstetricia.segurity.obstetricia.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.obstetricia.segurity.obstetricia.modelo.ControlEmbarazo;

public interface ControlEmbarazoRepositorio extends JpaRepository<ControlEmbarazo, Long> {

        List<ControlEmbarazo> findByPacienteId(Long pacienteId);
@Query("SELECT e FROM ControlEmbarazo e WHERE e.paciente.id = :pacienteId ORDER BY e.fechaFinEmbarazo DESC")
Optional<ControlEmbarazo> findUltimoPorPacienteId(@Param("pacienteId") Long pacienteId);


}