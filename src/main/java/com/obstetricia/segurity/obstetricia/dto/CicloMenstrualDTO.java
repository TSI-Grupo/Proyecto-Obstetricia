package com.obstetricia.segurity.obstetricia.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CicloMenstrualDTO {

    @NotNull
    private String primerDiaPeriodo;

    @NotNull
    @Min(value = 0, message = "La duración del ciclo debe ser mayor o igual a 0.")
    private Integer duracionCiclo;

    @NotNull
    private String flujo;

    @NotNull
    private String mocoVaginal;

    public Integer getDuracionCiclo() {
        return duracionCiclo;
    }

    public void setDuracionCiclo(Integer duracionCiclo) {
        this.duracionCiclo = duracionCiclo;
    }

    public LocalDate getPrimerDiaPeriodo() {
        throw new UnsupportedOperationException("Unimplemented method 'getPrimerDiaPeriodo'");
    }

    public String getFlujo() {
        throw new UnsupportedOperationException("Unimplemented method 'getFlujo'");
    }

    public String getMocoVaginal() {
        throw new UnsupportedOperationException("Unimplemented method 'getMocoVaginal'");
    }

    public String getSintomas() {
        throw new UnsupportedOperationException("Unimplemented method 'getSintomas'");
    }

    public String getEstadoAnimo() {
        throw new UnsupportedOperationException("Unimplemented method 'getEstadoAnimo'");
    }

    public void setPrimerDiaPeriodo(String primerDiaPeriodo) {
        this.primerDiaPeriodo = primerDiaPeriodo;
    }

    public void setFlujo(String flujo) {
        this.flujo = flujo;
    }

    public void setMocoVaginal(String mocoVaginal) {
        this.mocoVaginal = mocoVaginal;
    }

    

    //private String[] sintomas;
    //private String otroSintoma;
    //private String[] estadoAnimo;

    // Getters y Setters
}