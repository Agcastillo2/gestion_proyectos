package com.espe.distri.gestionproyectos.proyecto.services;

import com.espe.distri.gestionproyectos.proyecto.models.DTO.UsuarioDTO;
import com.espe.distri.gestionproyectos.proyecto.models.Proyecto;
import com.espe.distri.gestionproyectos.proyecto.models.relacion.ProyectoUsuario;

import java.util.List;
import java.util.Optional;

public interface ProyectoService {
    Proyecto saveProyecto(Proyecto proyecto); // Guardar un proyecto
    Optional<Proyecto> getProyectoById(Long id); // Obtener un proyecto por su ID
    List<Proyecto> getAllProyectos(); // Obtener todos los proyectos
    void deleteProyecto(Long id); // Eliminar un proyecto por su ID
    boolean existsByNombreProyecto(String nombreProyecto); // Verificar si ya existe un proyecto con el mismo nombre

    Proyecto updateProyecto(Long id, Proyecto nuevoProyecto); // Modificar un proyecto

    Optional<ProyectoUsuario> assignUsuario(Long proyectoId, Long usuarioId);
    void deleteProyectoUsuario(Long proyectoId, Long usuarioId);
    Optional<UsuarioDTO> findUsuarioByProyectoId(Long proyectoId);
    List<Proyecto> findProyectosByUsuarioId(Long usuarioId);

}