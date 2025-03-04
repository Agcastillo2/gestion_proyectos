package com.espe.distri.gestionproyectos.completado.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "completados")
@Data
public class Completado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_completado")
    private Long idCompletado;

    @NotNull(message = "El porcentaje completado es obligatorio")
    @Min(value = 0, message = "El porcentaje completado no puede ser menor que 0")
    @Max(value = 100, message = "El porcentaje completado no puede ser mayor que 100")
    @Column(name = "porcentaje_completado", nullable = false)
    private Integer porcentajeCompletado;

    @NotNull(message = "La fecha de actualización es obligatoria")
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @Size(max = 500, message = "Los comentarios no pueden exceder los 500 caracteres")
    @Column(name = "comentarios", length = 500)
    private String comentarios;

    @PrePersist
    public void prePersist() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Long getIdCompletado() {
        return idCompletado;
    }

    public void setIdCompletado(Long idCompletado) {
        this.idCompletado = idCompletado;
    }

    public Integer getPorcentajeCompletado() {
        return porcentajeCompletado;
    }

    public void setPorcentajeCompletado(Integer porcentajeCompletado) {
        this.porcentajeCompletado = porcentajeCompletado;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}