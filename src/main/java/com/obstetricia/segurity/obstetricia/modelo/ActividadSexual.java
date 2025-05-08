package com.obstetricia.segurity.obstetricia.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ActividadSexual {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Paciente paciente;

    private LocalDate fecha;
    private Boolean usoProteccion;
    private String frecuencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getUsoProteccion() {
        return usoProteccion;
    }

    public void setUsoProteccion(Boolean usoProteccion) {
        this.usoProteccion = usoProteccion;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

}
