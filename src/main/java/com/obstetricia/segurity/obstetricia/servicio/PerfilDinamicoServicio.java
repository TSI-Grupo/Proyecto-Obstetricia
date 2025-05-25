package com.obstetricia.segurity.obstetricia.servicio;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;
import com.obstetricia.segurity.obstetricia.modelo.ControlEmbarazo;
import com.obstetricia.segurity.obstetricia.modelo.EstadoPerfil;
import com.obstetricia.segurity.obstetricia.repositorio.CicloMenstrualRepositorio;
import com.obstetricia.segurity.obstetricia.repositorio.ControlEmbarazoRepositorio;

@Service
public class PerfilDinamicoServicio {

    @Autowired
    private CicloMenstrualRepositorio cicloRepo;

    @Autowired
    private ControlEmbarazoRepositorio embarazoRepo;

    public EstadoPerfil determinarPerfil(Long pacienteId) {
       boolean tieneCiclo = cicloRepo.existsByPacienteId(pacienteId);
        Optional<ControlEmbarazo> embarazoOpt = embarazoRepo.findUltimoPorPacienteId(pacienteId); // ordenado por fecha

        if (tieneCiclo && embarazoOpt.isEmpty()) {
            return EstadoPerfil.MENSTRUANTE;
        }

        if (embarazoOpt.isPresent()) {
            LocalDate fechaFinEmbarazo = embarazoOpt.get().getFechaFinEmbarazo();


            Optional<CicloMenstrual> cicloPostEmbarazo = cicloRepo.findPrimeroDespuesDeFecha(pacienteId, fechaFinEmbarazo);
            if (cicloPostEmbarazo.isPresent()) {
                return EstadoPerfil.POSTPARTO;
            } else {
                return EstadoPerfil.EMBARAZADA;
            }
        }

        return EstadoPerfil.MENOPÁUSICA;
    }
}
