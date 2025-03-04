package com.espe.distri.gestionproyectos.tarea.models.relacion;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarea_usuario")
@Data
public class TareaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId; // Cambiado de idUsuario a usuarioId
    private String usuarioNombre; // Cambiado de nombre a usuarioNombre
    private String usuarioEmail; // Cambiado de email a usuarioEmail

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

}
