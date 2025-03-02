package com.espe.distri.gestionproyectos.usuario.models.relacion;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario_rol")
@Data
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rolIdRol;
    private String rolNombreRol;
    private String rolDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRolIdRol() {
        return rolIdRol;
    }

    public void setRolIdRol(Long rolIdRol) {
        this.rolIdRol = rolIdRol;
    }

    public String getRolNombreRol() {
        return rolNombreRol;
    }

    public void setRolNombreRol(String rolNombreRol) {
        this.rolNombreRol = rolNombreRol;
    }

    public String getRolDescripcion() {
        return rolDescripcion;
    }

    public void setRolDescripcion(String rolDescripcion) {
        this.rolDescripcion = rolDescripcion;
    }
}
