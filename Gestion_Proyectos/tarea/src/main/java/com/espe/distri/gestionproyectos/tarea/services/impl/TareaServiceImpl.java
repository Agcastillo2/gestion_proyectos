package com.espe.distri.gestionproyectos.tarea.services.impl;

import com.espe.distri.gestionproyectos.tarea.models.DTO.ProyectoDTO;
import com.espe.distri.gestionproyectos.tarea.models.DTO.UsuarioDTO;
import com.espe.distri.gestionproyectos.tarea.models.Tarea;
import com.espe.distri.gestionproyectos.tarea.models.relacion.TareaProyecto;
import com.espe.distri.gestionproyectos.tarea.models.relacion.TareaUsuario;
import com.espe.distri.gestionproyectos.tarea.repositories.TareaRepository;
import com.espe.distri.gestionproyectos.tarea.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    public Optional<TareaUsuario> assignUsuario(Long tareaId, Long usuarioId) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(tareaId);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();

            // Validar si el usuario ya está asignado
            if (tarea.getTareaUsuario() != null && tarea.getTareaUsuario().getUsuarioId().equals(usuarioId)) {
                throw new IllegalArgumentException("Este usuario ya está asignado a esta tarea");
            }

            try {
                // Llamada al microservicio de usuario
                UsuarioDTO usuario = restTemplate.getForObject("http://localhost:8002/api/usuarios/" + usuarioId, UsuarioDTO.class);

                if (usuario == null) {
                    throw new IllegalArgumentException("No se encuentra el usuario solicitado o no existe");
                }

                // Crear el objeto TareaUsuario
                TareaUsuario tareaUsuario = new TareaUsuario();
                tareaUsuario.setUsuarioId(usuario.getId());
                tareaUsuario.setUsuarioNombre(usuario.getNombre());
                tareaUsuario.setUsuarioEmail(usuario.getEmail());

                // Asignar el usuario a la tarea (relación OneToOne)
                tarea.setTareaUsuario(tareaUsuario);
                tareaRepository.save(tarea);

                return Optional.of(tareaUsuario);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteTareaUsuario(Long tareaId, Long usuarioId) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(tareaId);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();

            // Validar si el usuario asignado es el correcto
            if (tarea.getTareaUsuario() != null && tarea.getTareaUsuario().getUsuarioId().equals(usuarioId)) {
                // Eliminar la relación, ya que es OneToOne
                tarea.setTareaUsuario(null);
                tareaRepository.save(tarea);
            }
        }
    }

    public Optional<UsuarioDTO> findUsuarioByTareaId(Long tareaId) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(tareaId);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();

            // Obtener el TareaUsuario asignado
            TareaUsuario tareaUsuario = tarea.getTareaUsuario();
            if (tareaUsuario != null) {
                // Llamada al microservicio de usuarios para obtener los detalles del usuario
                UsuarioDTO usuario = restTemplate.getForObject("http://localhost:8002/api/usuarios/" + tareaUsuario.getUsuarioId(), UsuarioDTO.class);
                return Optional.ofNullable(usuario);
            }
        }
        return Optional.empty();  // Si no tiene ningún usuario asignado
    }

    public List<Tarea> findTareasByUsuarioId(Long usuarioId) {
        return tareaRepository.findTareasByUsuarioId(usuarioId);
    }

    public Optional<TareaProyecto> assignProyecto(Long tareaId, Long proyectoId) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(tareaId);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();

            // Validar si el proyecto ya está asignado
            if (tarea.getTareaProyecto() != null && tarea.getTareaProyecto().getProyectoId().equals(proyectoId)) {
                throw new IllegalArgumentException("Este proyecto ya está asignado a esta tarea");
            }

            try {
                // Llamada al microservicio de proyecto
                ProyectoDTO proyecto = restTemplate.getForObject("http://localhost:8003/api/proyectos/" + proyectoId, ProyectoDTO.class);

                if (proyecto == null) {
                    throw new IllegalArgumentException("No se encuentra el proyecto solicitado o no existe");
                }

                TareaProyecto tareaProyecto = new TareaProyecto();
                tareaProyecto.setProyectoId(proyecto.getIdProyecto());
                tareaProyecto.setProyectoNombre(proyecto.getNombreProyecto());
                tareaProyecto.setProyectoDescripcion(proyecto.getDescripcion());
                tareaProyecto.setProyectoFechaInicio(proyecto.getFechaInicio());
                tareaProyecto.setProyectoFechaFinEstimada(proyecto.getFechaFinEstimada());
                tareaProyecto.setProyectoFechaFinReal(proyecto.getFechaFinReal());
                tareaProyecto.setProyectoEstado(proyecto.getEstado());
                tarea.setTareaProyecto(tareaProyecto);
                tareaRepository.save(tarea);

                return Optional.of(tareaProyecto);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteTareaProyecto(Long tareaId, Long proyectoId) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(tareaId);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();

            // Validar si el proyecto asignado es el correcto
            if (tarea.getTareaProyecto() != null && tarea.getTareaProyecto().getProyectoId().equals(proyectoId)) {
                // Eliminar la relación, ya que es OneToOne
                tarea.setTareaProyecto(null);
                tareaRepository.save(tarea);
            }
        }
    }

    public Optional<ProyectoDTO> findProyectoByTareaId(Long tareaId) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(tareaId);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();

            // Obtener el TareaProyecto asignado
            TareaProyecto tareaProyecto = tarea.getTareaProyecto();
            if (tareaProyecto != null) {
                // Llamada al microservicio de proyectos para obtener los detalles del proyecto
                ProyectoDTO proyecto = restTemplate.getForObject("http://localhost:8003/api/proyectos/" + tareaProyecto.getProyectoId(), ProyectoDTO.class);
                return Optional.ofNullable(proyecto);
            }
        }
        return Optional.empty();  // Si no tiene ningún proyecto asignado
    }

    public List<Tarea> findTareasByProyectoId(Long proyectoId) {
        return tareaRepository.findTareasByProyectoId(proyectoId);
    }

}