package com.obstetricia.segurity.obstetricia.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ciclo_menstrual")
public class CicloMenstrual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate primerDiaPeriodo;

    // Validacion de fecha implementada
    @AssertTrue(message = "La fecha del primer día no puede ser futura.")
    public boolean isPrimerDiaValido() {
        return primerDiaPeriodo == null || !primerDiaPeriodo.isAfter(LocalDate.now());
    }

    @NotNull
    @Min(1)
    private Integer duracionCiclo; 

    private String flujo; 

    private String mocoVaginal; 

    private String sintomas; 

    private String estadoAnimo;

    @Transient
    private String sintomasFormateados;

    @Transient
    private String estadoAnimoFormateado;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id") // FK a Usuario
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

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

    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
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

    public String getSintomasFormateados() {
        return sintomasFormateados;
    }
    
    public void setSintomasFormateados(String sintomasFormateados) {
        this.sintomasFormateados = sintomasFormateados;
    }
    
    public String getEstadoAnimoFormateado() {
        return estadoAnimoFormateado;
    }
    
    public void setEstadoAnimoFormateado(String estadoAnimoFormateado) {
        this.estadoAnimoFormateado = estadoAnimoFormateado;
    }
    
}
