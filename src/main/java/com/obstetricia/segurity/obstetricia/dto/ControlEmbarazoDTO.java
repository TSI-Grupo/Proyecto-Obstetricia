package com.obstetricia.segurity.obstetricia.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class ControlEmbarazoDTO {

    @NotNull
    private LocalDate ultimoDiaMenstruacion;

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long usuarioId;

    public LocalDate getUltimoDiaMenstruacion() {
        return ultimoDiaMenstruacion;
    }

    public void setUltimoDiaMenstruacion(LocalDate ultimoDiaMenstruacion) {
        this.ultimoDiaMenstruacion = ultimoDiaMenstruacion;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}