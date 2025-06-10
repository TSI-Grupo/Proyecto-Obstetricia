package com.obstetricia.segurity.obstetricia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obstetricia.segurity.obstetricia.modelo.IngestaAnticonceptiva;

@Repository
public interface IngestaAnticonceptivaRepositorio extends JpaRepository<IngestaAnticonceptiva, Long> {
    List<IngestaAnticonceptiva> findByPacienteId(Long pacienteId);
}