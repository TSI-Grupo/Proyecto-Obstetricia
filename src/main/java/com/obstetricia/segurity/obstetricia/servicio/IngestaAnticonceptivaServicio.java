package com.obstetricia.segurity.obstetricia.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.modelo.IngestaAnticonceptiva;
import com.obstetricia.segurity.obstetricia.repositorio.IngestaAnticonceptivaRepositorio;

@Service
public class IngestaAnticonceptivaServicio {

    @Autowired
    private IngestaAnticonceptivaRepositorio repositorio;

    public void guardarIngesta(IngestaAnticonceptiva ingesta) {
        repositorio.save(ingesta);
    }

    public List<IngestaAnticonceptiva> listarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId);
    }

    public List<IngestaAnticonceptiva> listarTodas() {
        return repositorio.findAll();
    }

    public void eliminarPorId(Long id) {
        repositorio.deleteById(id);
    }
}