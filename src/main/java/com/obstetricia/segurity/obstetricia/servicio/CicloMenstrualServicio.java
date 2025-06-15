package com.obstetricia.segurity.obstetricia.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.dto.CicloMenstrualDTO;
import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;
import com.obstetricia.segurity.obstetricia.repositorio.CicloMenstrualRepositorio;

@Service
public class CicloMenstrualServicio {

    @Autowired
    private CicloMenstrualRepositorio repositorio;
     
    public CicloMenstrual registrarCiclo(CicloMenstrual ciclo){
        return repositorio.save(ciclo);
    }

    public CicloMenstrual registrarCicloDto(CicloMenstrualDTO ciclodto){
        CicloMenstrual ciclo = new CicloMenstrual();
        ciclo.setDuracionCiclo(ciclodto.getDuracionCiclo());
        ciclo.setPrimerDiaPeriodo(ciclodto.getPrimerDiaPeriodo());
        ciclo.setFlujo(ciclodto.getFlujo());
        ciclo.setMocoVaginal(ciclodto.getMocoVaginal());
        ciclo.setSintomas(ciclodto.getSintomas());
        ciclo.setEstadoAnimo(ciclodto.getEstadoAnimo());
        return repositorio.save(ciclo);
    }

    public List<CicloMenstrual> obtenerTodos() {
        return repositorio.findAll();
    }

    public Optional<CicloMenstrual> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public void eliminarPorId(Long id) {
        repositorio.deleteById(id);
    }

}
