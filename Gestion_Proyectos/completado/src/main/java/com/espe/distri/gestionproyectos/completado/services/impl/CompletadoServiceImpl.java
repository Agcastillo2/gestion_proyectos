package com.espe.distri.gestionproyectos.completado.services.impl;

import com.espe.distri.gestionproyectos.completado.models.Completado;
import com.espe.distri.gestionproyectos.completado.models.DTO.TareaDTO;
import com.espe.distri.gestionproyectos.completado.models.relacion.CompletadoTarea;
import com.espe.distri.gestionproyectos.completado.repositories.CompletadoRepository;
import com.espe.distri.gestionproyectos.completado.services.CompletadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompletadoServiceImpl implements CompletadoService {

    @Autowired
    private CompletadoRepository completadoRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Completado saveCompletado(Completado completado) {
        return completadoRepository.save(completado);
    }

    @Override
    public Optional<Completado> getCompletadoById(Long id) {
        return completadoRepository.findById(id);
    }

    @Override
    public List<Completado> getAllCompletados() {
        return completadoRepository.findAll();
    }

    @Override
    public void deleteCompletado(Long id) {
        completadoRepository.deleteById(id);
    }

    @Override
    public Completado updateCompletado(Long id, Completado nuevoCompletado) {
        return completadoRepository.findById(id).map(completado -> {
            completado.setPorcentajeCompletado(nuevoCompletado.getPorcentajeCompletado());
            completado.setComentarios(nuevoCompletado.getComentarios());
            completado.setFechaActualizacion(LocalDateTime.now());
            return completadoRepository.save(completado);
        }).orElse(null);
    }

    public Optional<CompletadoTarea> assignTarea(Long completadoId, Long tareaId) {
        Optional<Completado> optionalCompletado = completadoRepository.findById(completadoId);
        if (optionalCompletado.isPresent()) {
            Completado completado = optionalCompletado.get();

            // Validar si la tarea ya está asignada
            if (completado.getCompletadoTarea() != null && completado.getCompletadoTarea().getTareaId().equals(tareaId)) {
                throw new IllegalArgumentException("Esta tarea ya está asignada a este completado");
            }

            try {
                // Llamada al microservicio de tarea
                TareaDTO tarea = restTemplate.getForObject("http://localhost:8004/api/tareas/" + tareaId, TareaDTO.class);

                if (tarea == null) {
                    throw new IllegalArgumentException("No se encuentra la tarea solicitada o no existe");
                }

                // Crear el objeto CompletadoTarea
                CompletadoTarea completadoTarea = new CompletadoTarea();
                completadoTarea.setTareaId(tarea.getIdTarea());
                completadoTarea.setTareaNombre(tarea.getNombreTarea());
                completadoTarea.setTareaDescripcion(tarea.getDescripcion());
                completadoTarea.setTareaFechaInicio(tarea.getFechaInicio());
                completadoTarea.setTareaFechaFinEstimada(tarea.getFechaFinEstimada());
                completadoTarea.setTareaFechaFinReal(tarea.getFechaFinReal());
                completadoTarea.setTareaEstado(tarea.getEstado());

                // Asignar la tarea al completado (relación OneToOne)
                completado.setCompletadoTarea(completadoTarea);
                completadoRepository.save(completado);

                return Optional.of(completadoTarea);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteCompletadoTarea(Long completadoId, Long tareaId) {
        Optional<Completado> optionalCompletado = completadoRepository.findById(completadoId);
        if (optionalCompletado.isPresent()) {
            Completado completado = optionalCompletado.get();

            // Validar si la tarea asignada es la correcta
            if (completado.getCompletadoTarea() != null && completado.getCompletadoTarea().getTareaId().equals(tareaId)) {
                // Eliminar la relación, ya que es OneToOne
                completado.setCompletadoTarea(null);
                completadoRepository.save(completado);
            }
        }
    }

    public Optional<TareaDTO> findTareaByCompletadoId(Long completadoId) {
        Optional<Completado> optionalCompletado = completadoRepository.findById(completadoId);
        if (optionalCompletado.isPresent()) {
            Completado completado = optionalCompletado.get();

            // Obtener el CompletadoTarea asignado
            CompletadoTarea completadoTarea = completado.getCompletadoTarea();
            if (completadoTarea != null) {
                // Llamada al microservicio de tareas para obtener los detalles de la tarea
                TareaDTO tarea = restTemplate.getForObject("http://localhost:8004/api/tareas/" + completadoTarea.getTareaId(), TareaDTO.class);
                return Optional.ofNullable(tarea);
            }
        }
        return Optional.empty();  // Si no tiene ninguna tarea asignada
    }

    public List<Completado> findCompletadosByTareaId(Long tareaId) {
        return completadoRepository.findCompletadosByTareaId(tareaId);
    }

}