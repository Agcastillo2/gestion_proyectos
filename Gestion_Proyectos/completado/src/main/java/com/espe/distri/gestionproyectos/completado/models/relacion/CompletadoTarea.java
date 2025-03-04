package com.espe.distri.gestionproyectos.completado.models.relacion;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "completado_usuario")
@Data
public class CompletadoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tareaId;
    private String tareaNombre;
    private String tareaDescripcion;
    private LocalDate tareaFechaInicio;
    private LocalDate tareaFechaFinEstimada;
    private LocalDate tareaFechaFinReal;
    private String  tareaEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTareaId() {
        return tareaId;
    }

    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }

    public String getTareaNombre() {
        return tareaNombre;
    }

    public void setTareaNombre(String tareaNombre) {
        this.tareaNombre = tareaNombre;
    }

    public String getTareaDescripcion() {
        return tareaDescripcion;
    }

    public void setTareaDescripcion(String tareaDescripcion) {
        this.tareaDescripcion = tareaDescripcion;
    }

    public LocalDate getTareaFechaInicio() {
        return tareaFechaInicio;
    }

    public void setTareaFechaInicio(LocalDate tareaFechaInicio) {
        this.tareaFechaInicio = tareaFechaInicio;
    }

    public LocalDate getTareaFechaFinEstimada() {
        return tareaFechaFinEstimada;
    }

    public void setTareaFechaFinEstimada(LocalDate tareaFechaFinEstimada) {
        this.tareaFechaFinEstimada = tareaFechaFinEstimada;
    }

    public LocalDate getTareaFechaFinReal() {
        return tareaFechaFinReal;
    }

    public void setTareaFechaFinReal(LocalDate tareaFechaFinReal) {
        this.tareaFechaFinReal = tareaFechaFinReal;
    }

    public String getTareaEstado() {
        return tareaEstado;
    }

    public void setTareaEstado(String tareaEstado) {
        this.tareaEstado = tareaEstado;
    }
}
