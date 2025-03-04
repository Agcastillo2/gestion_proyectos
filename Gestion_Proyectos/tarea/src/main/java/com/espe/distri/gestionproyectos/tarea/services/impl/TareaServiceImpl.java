package com.espe.distri.gestionproyectos.tarea.services.impl;

import com.espe.distri.gestionproyectos.tarea.models.Tarea;
import com.espe.distri.gestionproyectos.tarea.repositories.TareaRepository;
import com.espe.distri.gestionproyectos.tarea.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public Tarea saveTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Optional<Tarea> getTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    @Override
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public void deleteTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombreTarea(String nombreTarea) {
        return tareaRepository.existsByNombreTarea(nombreTarea);
    }

    @Override
    public Tarea updateTarea(Long id, Tarea nuevaTarea) {
        Optional<Tarea> tareaExistente = tareaRepository.findById(id);

        if (tareaExistente.isPresent()) {
            Tarea tarea = tareaExistente.get();
            tarea.setNombreTarea(nuevaTarea.getNombreTarea());
            tarea.setDescripcion(nuevaTarea.getDescripcion());
            tarea.setFechaInicio(nuevaTarea.getFechaInicio());
            tarea.setFechaFinEstimada(nuevaTarea.getFechaFinEstimada());
            tarea.setFechaFinReal(nuevaTarea.getFechaFinReal());
            tarea.setEstado(nuevaTarea.getEstado());
            return tareaRepository.save(tarea);
        }

        // Si no existe la tarea con ese ID, devolver null o lanzar una excepción
        return null;
    }
}