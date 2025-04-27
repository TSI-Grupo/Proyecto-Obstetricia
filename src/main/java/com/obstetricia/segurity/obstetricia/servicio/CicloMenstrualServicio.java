package com.obstetricia.segurity.obstetricia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;
import com.obstetricia.segurity.obstetricia.repositorio.CicloMenstrualRepositorio;

@Service
public class CicloMenstrualServicio {

    @Autowired
    private CicloMenstrualRepositorio repositorio;
     
    public CicloMenstrual registrarCiclo(CicloMenstrual ciclo){
        return repositorio.save(ciclo);
    }
}
