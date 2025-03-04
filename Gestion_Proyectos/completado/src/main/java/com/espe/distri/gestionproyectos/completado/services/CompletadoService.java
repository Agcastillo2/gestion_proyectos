package com.espe.distri.gestionproyectos.completado.services;

import com.espe.distri.gestionproyectos.completado.models.Completado;
import com.espe.distri.gestionproyectos.completado.models.DTO.TareaDTO;
import com.espe.distri.gestionproyectos.completado.models.relacion.CompletadoTarea;

import java.util.List;
import java.util.Optional;

public interface CompletadoService {
    Completado saveCompletado(Completado completado); // Guardar un registro de completado
    Optional<Completado> getCompletadoById(Long id); // Obtener un registro por su ID
    List<Completado> getAllCompletados(); // Obtener todos los registros
    void deleteCompletado(Long id); // Eliminar un registro por su ID

    Completado updateCompletado(Long id, Completado nuevoCompletado);

    Optional<CompletadoTarea> assignTarea(Long completadoId, Long tareaId);
    void deleteCompletadoTarea(Long completadoId, Long tareaId);
    Optional<TareaDTO> findTareaByCompletadoId(Long completadoId);
    Optional<Completado> findCompletadoByTareaId(Long tareaId);

}