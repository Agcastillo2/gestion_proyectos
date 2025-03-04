package com.espe.distri.gestionproyectos.tarea.services;

import com.espe.distri.gestionproyectos.tarea.models.DTO.ProyectoDTO;
import com.espe.distri.gestionproyectos.tarea.models.DTO.UsuarioDTO;
import com.espe.distri.gestionproyectos.tarea.models.Tarea;
import com.espe.distri.gestionproyectos.tarea.models.relacion.TareaProyecto;
import com.espe.distri.gestionproyectos.tarea.models.relacion.TareaUsuario;

import java.util.List;
import java.util.Optional;

public interface TareaService {
    Tarea saveTarea(Tarea tarea); // Guardar una tarea
    Optional<Tarea> getTareaById(Long id); // Obtener una tarea por su ID
    List<Tarea> getAllTareas(); // Obtener todas las tareas
    void deleteTarea(Long id); // Eliminar una tarea por su ID
    boolean existsByNombreTarea(String nombreTarea);

    Tarea updateTarea(Long id, Tarea nuevaTarea);// Verificar si ya existe una tarea con el mismo nombre

    Optional<TareaUsuario> assignUsuario(Long tareaId, Long usuarioId);
    void deleteTareaUsuario(Long tareaId, Long usuarioId);
    Optional<UsuarioDTO> findUsuarioByTareaId(Long tareaId);
    Optional<Tarea> findTareaByUsuarioId(Long usuarioId);

    Optional<TareaProyecto> assignProyecto(Long tareaId, Long proyectoId);
    void deleteTareaProyecto(Long tareaId, Long proyectoId);
    Optional<ProyectoDTO> findProyectoByTareaId(Long tareaId);
    Optional<Tarea> findTareaByProyectoId(Long proyectoId);





}