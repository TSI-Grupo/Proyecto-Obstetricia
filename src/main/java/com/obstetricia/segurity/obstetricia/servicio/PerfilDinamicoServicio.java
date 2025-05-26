package com.obstetricia.segurity.obstetricia.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.modelo.EstadoPerfil;
import com.obstetricia.segurity.obstetricia.modelo.ControlEmbarazo;
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

        // Obtener la lista de embarazos ordenada por fecha fin
        List<ControlEmbarazo> embarazos = embarazoRepo.findTop1ByPacienteIdOrderByFechaFinEmbarazoDesc(pacienteId);

        boolean tieneEmbarazo = !embarazos.isEmpty();

        if(tieneCiclo && tieneEmbarazo){
            return EstadoPerfil.MENSTRUANTE_EMBARAZADA;
        }

        if (tieneCiclo && !tieneEmbarazo) {
            return EstadoPerfil.MENSTRUANTE;
        }

        if (tieneEmbarazo) {
            return EstadoPerfil.EMBARAZADA;
        }

        return EstadoPerfil.MENOPÁUSICA;
    }
}
