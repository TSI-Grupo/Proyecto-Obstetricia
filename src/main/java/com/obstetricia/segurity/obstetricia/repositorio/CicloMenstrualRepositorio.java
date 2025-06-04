package com.obstetricia.segurity.obstetricia.repositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;

public interface CicloMenstrualRepositorio extends JpaRepository<CicloMenstrual, Long>{
    List<CicloMenstrual> findByPacienteId(Long pacienteId);
    boolean existsByPacienteId(Long pacienteId);
@Query("SELECT c FROM CicloMenstrual c WHERE c.paciente.id = :pacienteId AND c.primerDiaPeriodo > :fecha ORDER BY c.primerDiaPeriodo ASC")
Optional<CicloMenstrual> findPrimeroDespuesDeFecha(@Param("pacienteId") Long pacienteId, @Param("fecha") LocalDate fecha);

}

