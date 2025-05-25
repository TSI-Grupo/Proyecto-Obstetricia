package com.obstetricia.segurity.obstetricia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;

public interface CicloMenstrualRepositorio extends JpaRepository<CicloMenstrual, Long>{
    List<CicloMenstrual> findByPacienteId(Long pacienteId);
}
