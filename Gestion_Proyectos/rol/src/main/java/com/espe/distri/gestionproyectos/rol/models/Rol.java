package com.espe.distri.gestionproyectos.rol.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @NotNull(message = "El nombre del rol es obligatorio")
    @Size(min = 1, max = 50, message = "El nombre del rol debe tener entre 1 y 50 caracteres")
    @Column(name = "nombre_rol", nullable = false, length = 50)
    private String nombreRol;

    @Size(max = 255, message = "La descripción del rol no puede exceder los 255 caracteres")
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // Getters y Setters (generados automáticamente por Lombok @Data)

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}