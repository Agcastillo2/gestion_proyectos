package com.espe.distri.gestionproyectos.tarea.services;

import com.espe.distri.gestionproyectos.tarea.models.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaService {
    Tarea saveTarea(Tarea tarea); // Guardar una tarea
    Optional<Tarea> getTareaById(Long id); // Obtener una tarea por su ID
    List<Tarea> getAllTareas(); // Obtener todas las tareas
    void deleteTarea(Long id); // Eliminar una tarea por su ID
    boolean existsByNombreTarea(String nombreTarea);

    Tarea updateTarea(Long id, Tarea nuevaTarea);// Verificar si ya existe una tarea con el mismo nombre
}