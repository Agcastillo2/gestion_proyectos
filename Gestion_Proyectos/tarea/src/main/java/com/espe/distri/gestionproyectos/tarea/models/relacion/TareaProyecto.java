package com.espe.distri.gestionproyectos.tarea.models.relacion;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarea_proyecto")
@Data
public class TareaProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long proyectoId;
    private String proyectoNombre;
    private String proyectoDescripcion;
    private LocalDate proyectoFechaInicio;
    private LocalDate proyectoFechaFinEstimada;
    private LocalDate proyectoFechaFinReal;
    private String proyectoEstado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getProyectoNombre() {
        return proyectoNombre;
    }

    public void setProyectoNombre(String proyectoNombre) {
        this.proyectoNombre = proyectoNombre;
    }

    public String getProyectoDescripcion() {
        return proyectoDescripcion;
    }

    public void setProyectoDescripcion(String proyectoDescripcion) {
        this.proyectoDescripcion = proyectoDescripcion;
    }

    public LocalDate getProyectoFechaInicio() {
        return proyectoFechaInicio;
    }

    public void setProyectoFechaInicio(LocalDate proyectoFechaInicio) {
        this.proyectoFechaInicio = proyectoFechaInicio;
    }

    public LocalDate getProyectoFechaFinEstimada() {
        return proyectoFechaFinEstimada;
    }

    public void setProyectoFechaFinEstimada(LocalDate proyectoFechaFinEstimada) {
        this.proyectoFechaFinEstimada = proyectoFechaFinEstimada;
    }

    public LocalDate getProyectoFechaFinReal() {
        return proyectoFechaFinReal;
    }

    public void setProyectoFechaFinReal(LocalDate proyectoFechaFinReal) {
        this.proyectoFechaFinReal = proyectoFechaFinReal;
    }

    public String getProyectoEstado() {
        return proyectoEstado;
    }

    public void setProyectoEstado(String proyectoEstado) {
        this.proyectoEstado = proyectoEstado;
    }
}
