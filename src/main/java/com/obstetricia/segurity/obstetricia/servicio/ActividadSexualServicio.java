package com.obstetricia.segurity.obstetricia.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.modelo.ActividadSexual;
import com.obstetricia.segurity.obstetricia.repositorio.ActividadSexualRepositorio;

@Service
public class ActividadSexualServicio {

    @Autowired
    private ActividadSexualRepositorio actividadSexualRepository;

    public void guardar(ActividadSexual actividadSexual) {
        actividadSexualRepository.save(actividadSexual);
    }

    public List<ActividadSexual> obtenerTodas() {
        return actividadSexualRepository.findAll();
    }

    public Optional<ActividadSexual> obtenerPorId(Long id) {
        return actividadSexualRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        actividadSexualRepository.deleteById(id);
    }
}
