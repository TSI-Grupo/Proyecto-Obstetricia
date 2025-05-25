package com.obstetricia.segurity.obstetricia.modelo;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "control_embarazo")
public class ControlEmbarazo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer semanas;

    private Integer trimestre;

    private LocalDate ultimoDiaMenstruacion;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Transient
    public int calcularSemanas() {
        if (ultimoDiaMenstruacion == null) return 0;
        long dias = java.time.temporal.ChronoUnit.DAYS.between(ultimoDiaMenstruacion, LocalDate.now());
        return (int) (dias / 7);
    }

    @Transient
    public int calcularTrimestre() {
        int semanas = calcularSemanas();
        if (semanas < 13) return 1;
        if (semanas < 27) return 2;
        return 3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSemanas() {
        return semanas;
    }

    public void setSemanas(Integer semanas) {
        this.semanas = semanas;
    }

    public Integer getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(Integer trimestre) {
        this.trimestre = trimestre;
    }

    public LocalDate getUltimoDiaMenstruacion() {
        return ultimoDiaMenstruacion;
    }

    public void setUltimoDiaMenstruacion(LocalDate ultimoDiaMenstruacion) {
        this.ultimoDiaMenstruacion = ultimoDiaMenstruacion;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ControlEmbarazo(){

    }

    public ControlEmbarazo(Integer semanas, Integer trimestre, LocalDate ultimoDiaMenstruacion, Paciente paciente) {
        this.semanas = semanas;
        this.trimestre = trimestre;
        this.ultimoDiaMenstruacion = ultimoDiaMenstruacion;
        this.paciente = paciente;
    }

    public void setPaciente(Optional<Paciente> paciente2) {
        throw new UnsupportedOperationException("Unimplemented method 'setPaciente'");
    }

}