package com.obstetricia.segurity.obstetricia.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "ciclo_menstrual")
public class CicloMenstrual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate primerDiaPeriodo;

    private Integer duracionCiclo; // En días

    private String flujo; // Ej: ligero, moderado, abundante

    private String mocoVaginal; // Opcional: descripción o tipos

    private String sintomas; // Ej: dolor, cansancio, etc.

    private String estadoAnimo; // Ej: feliz, irritable, triste

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getPrimerDiaPeriodo() {
        return primerDiaPeriodo;
    }

    public void setPrimerDiaPeriodo(LocalDate primerDiaPeriodo) {
        this.primerDiaPeriodo = primerDiaPeriodo;
    }

    public Integer getDuracionCiclo() {
        return duracionCiclo;
    }

    public void setDuracionCiclo(Integer duracionCiclo) {
        this.duracionCiclo = duracionCiclo;
    }

    public String getFlujo() {
        return flujo;
    }

    public void setFlujo(String flujo) {
        this.flujo = flujo;
    }

    public String getMocoVaginal() {
        return mocoVaginal;
    }

    public void setMocoVaginal(String mocoVaginal) {
        this.mocoVaginal = mocoVaginal;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getEstadoAnimo() {
        return estadoAnimo;
    }

    public void setEstadoAnimo(String estadoAnimo) {
        this.estadoAnimo = estadoAnimo;
    }

    public CicloMenstrual() {
    }

    public CicloMenstrual( LocalDate primerDiaPeriodo, Integer duracionCiclo, String flujo,
            String mocoVaginal, String sintomas, String estadoAnimo) {

        this.primerDiaPeriodo = primerDiaPeriodo;
        this.duracionCiclo = duracionCiclo;
        this.flujo = flujo;
        this.mocoVaginal = mocoVaginal;
        this.sintomas = sintomas;
        this.estadoAnimo = estadoAnimo;
    }
    
}
