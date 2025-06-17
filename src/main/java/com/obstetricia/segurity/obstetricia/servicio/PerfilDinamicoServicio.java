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

import com.obstetricia.segurity.obstetricia.modelo.Paciente;
import com.obstetricia.segurity.obstetricia.repositorio.PacienteRepositorio;

@Service
public class PerfilDinamicoServicio {

    @Autowired
    private CicloMenstrualRepositorio cicloRepo;

    @Autowired
    private ControlEmbarazoRepositorio embarazoRepo;

    @Autowired
    private PacienteRepositorio pacienteRepo;

    public EstadoPerfil determinarPerfil(Long pacienteId) {
        Optional<Paciente> pacienteOpt = pacienteRepo.findById(pacienteId);
        if (pacienteOpt.isEmpty()) {
            return EstadoPerfil.ESTADO_NO_ASIGNADO;
        }

        Paciente paciente = pacienteOpt.get();
        int edad = calcularEdad(paciente.getFechaNacimiento());

        Optional<CicloMenstrual> ultimoCicloOpt = cicloRepo.findTopByPacienteIdOrderByPrimerDiaPeriodoDesc(pacienteId);
        List<ControlEmbarazo> embarazos = embarazoRepo.findTop1ByPacienteIdOrderByFechaFinEmbarazoDesc(pacienteId);

        boolean tieneEmbarazo = !embarazos.isEmpty();
        boolean tieneCiclo = ultimoCicloOpt.isPresent();

        boolean esMenopausica = false;

        if (!tieneCiclo && edad > 60) {
            esMenopausica = true;
        } else if (tieneCiclo) {
            LocalDate fechaUltimoCiclo = ultimoCicloOpt.get().getPrimerDiaPeriodo();
            long diasSinCiclo = ChronoUnit.DAYS.between(fechaUltimoCiclo, LocalDate.now());
            esMenopausica = (diasSinCiclo >= 365) && edad > 60;
        }

        if (!tieneCiclo && !tieneEmbarazo) {
            return EstadoPerfil.ESTADO_NO_ASIGNADO;
        }

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

        return EstadoPerfil.ESTADO_NO_ASIGNADO; // Fallback seguro
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        return (int) ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());
    }
}
