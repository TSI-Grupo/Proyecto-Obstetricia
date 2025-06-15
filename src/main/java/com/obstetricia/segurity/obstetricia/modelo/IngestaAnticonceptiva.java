package com.obstetricia.segurity.obstetricia.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "ingesta_anticonceptiva")
public class IngestaAnticonceptiva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private Boolean tomada;

    private Boolean olvidada = false;

    private String efectosAdversos;

    private String marca;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

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

    public Boolean isTomada() {
        return tomada;
    }

    public void setTomada(Boolean tomada) {
        this.tomada = tomada;
    }

    public Boolean isOlvidada() {
        return olvidada;
    }

    public void setOlvidada(Boolean olvidada) {
        this.olvidada = olvidada;
    }

    public String getEfectosAdversos() {
        return efectosAdversos;
    }

    public void setEfectosAdversos(String efectosAdversos) {
        this.efectosAdversos = efectosAdversos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public IngestaAnticonceptiva() {

    }

    public IngestaAnticonceptiva(LocalDate fecha, Boolean tomada, Boolean olvidada, String efectosAdversos, 
            String marca, Paciente paciente) {
        this.fecha = fecha;
        this.tomada = tomada;
        this.olvidada = olvidada;
        this.efectosAdversos = efectosAdversos;
        this.marca = marca;
        this.paciente = paciente;
    }

}