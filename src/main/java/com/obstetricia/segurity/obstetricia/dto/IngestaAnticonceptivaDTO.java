package com.obstetricia.segurity.obstetricia.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class IngestaAnticonceptivaDTO {
    
    @NotNull  
    private Long pacienteId;
    
    private Long id;
    
    @NotNull
    private LocalDate fecha;
    
    private boolean tomada;
    
    private boolean olvidada;
    
    private String efectosAdversos;

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isTomada() {
        return tomada;
    }

    public void setTomada(boolean tomada) {
        this.tomada = tomada;
    }

    public boolean isOlvidada() {
        return olvidada;
    }

    public void setOlvidada(boolean olvidada) {
        this.olvidada = olvidada;
    }

    public String getEfectosAdversos() {
        return efectosAdversos;
    }

    public void setEfectosAdversos(String efectosAdversos) {
        this.efectosAdversos = efectosAdversos;
    }

}
