package com.espe.distri.gestionproyectos.proyecto.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "proyectos")
@Data
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    @NotNull(message = "El nombre del proyecto es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre del proyecto debe tener entre 1 y 100 caracteres")
    @Column(name = "nombre_proyecto", nullable = false, length = 100)
    private String nombreProyecto;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha estimada de finalización es obligatoria")
    @Column(name = "fecha_fin_estimada", nullable = false)
    private LocalDate fechaFinEstimada;

    @Column(name = "fecha_fin_real")
    private LocalDate fechaFinReal; // Opcional

    @NotNull(message = "El estado del proyecto es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoProyecto estado;

    // Enum para el estado del proyecto
    public enum EstadoProyecto {
        EN_PROGRESO, COMPLETADO, CANCELADO, PENDIENTE
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinEstimada() {
        return fechaFinEstimada;
    }

    public void setFechaFinEstimada(LocalDate fechaFinEstimada) {
        this.fechaFinEstimada = fechaFinEstimada;
    }

    public LocalDate getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(LocalDate fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public EstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProyecto estado) {
        this.estado = estado;
    }
}