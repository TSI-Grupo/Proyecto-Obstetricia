package com.obstetricia.segurity.obstetricia.servicio;

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
}
