package com.obstetricia.segurity.obstetricia.servicio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obstetricia.segurity.obstetricia.modelo.EstadoPerfil;
import com.obstetricia.segurity.obstetricia.modelo.CicloMenstrual;
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
    boolean esMenopausica = false;

    Optional<CicloMenstrual> ultimoCicloOpt = cicloRepo.findTopByPacienteIdOrderByPrimerDiaPeriodoDesc(pacienteId);

    if (ultimoCicloOpt.isEmpty()) {
        esMenopausica = true; // Nunca ha tenido un ciclo registrado
    } else {
        LocalDate fechaUltimoCiclo = ultimoCicloOpt.get().getPrimerDiaPeriodo();
        long diasSinCiclo = ChronoUnit.DAYS.between(fechaUltimoCiclo, LocalDate.now());
        esMenopausica = diasSinCiclo >= 365; // 12 meses sin menstruación
    }

    List<ControlEmbarazo> embarazos = embarazoRepo.findTop1ByPacienteIdOrderByFechaFinEmbarazoDesc(pacienteId);
    boolean tieneEmbarazo = !embarazos.isEmpty();

    if (esMenopausica && tieneEmbarazo) {
        return EstadoPerfil.MENOPÁUSICA_EMBARAZADA;
    }

    if (!esMenopausica && tieneEmbarazo) {
        return EstadoPerfil.MENSTRUANTE_EMBARAZADA;
    }

    if (!esMenopausica && !tieneEmbarazo) {
        return EstadoPerfil.MENSTRUANTE;
    }

    if (esMenopausica && !tieneEmbarazo) {
        return EstadoPerfil.MENOPÁUSICA;
    }

    return EstadoPerfil.MENOPÁUSICA; // fallback por seguridad
}
}
