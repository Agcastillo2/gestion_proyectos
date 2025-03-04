package com.espe.distri.gestionproyectos.tarea.models.DTO;

import lombok.Data;


@Data
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String email;

    public Long getIdUsuario() {
        return id;
    }

    public void setIdUsuario(Long idUsuario) {
        this.id = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
