package com.espe.distri.gestionproyectos.proyecto.services.impl;

import com.espe.distri.gestionproyectos.proyecto.models.DTO.UsuarioDTO;
import com.espe.distri.gestionproyectos.proyecto.models.Proyecto;
import com.espe.distri.gestionproyectos.proyecto.models.relacion.ProyectoUsuario;
import com.espe.distri.gestionproyectos.proyecto.repositories.ProyectoRepository;
import com.espe.distri.gestionproyectos.proyecto.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Proyecto saveProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Optional<Proyecto> getProyectoById(Long id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }

    @Override
    public void deleteProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombreProyecto(String nombreProyecto) {
        return proyectoRepository.existsByNombreProyecto(nombreProyecto);
    }

    @Override
    public Proyecto updateProyecto(Long id, Proyecto nuevoProyecto) {
        Optional<Proyecto> proyectoExistente = proyectoRepository.findById(id);

        if (proyectoExistente.isPresent()) {
            Proyecto proyecto = proyectoExistente.get();
            proyecto.setNombreProyecto(nuevoProyecto.getNombreProyecto());
            proyecto.setDescripcion(nuevoProyecto.getDescripcion());
            proyecto.setFechaInicio(nuevoProyecto.getFechaInicio());
            proyecto.setFechaFinEstimada(nuevoProyecto.getFechaFinEstimada());
            proyecto.setFechaFinReal(nuevoProyecto.getFechaFinReal());
            proyecto.setEstado(nuevoProyecto.getEstado());
            return proyectoRepository.save(proyecto);
        }

        // Si no existe el proyecto con ese ID, devolver null o lanzar una excepción
        return null;
    }

    public Optional<ProyectoUsuario> assignUsuario(Long proyectoId, Long usuarioId) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(proyectoId);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();

            // Validar si el usuario ya está asignado
            if (proyecto.getProyectoUsuario() != null && proyecto.getProyectoUsuario().getUsuarioId().equals(usuarioId)) {
                throw new IllegalArgumentException("Este usuario ya está asignado a este proyecto");
            }

            try {
                // Llamada al microservicio de usuario
                UsuarioDTO usuario = restTemplate.getForObject("http://localhost:8002/api/usuarios/" + usuarioId, UsuarioDTO.class);

                if (usuario == null) {
                    throw new IllegalArgumentException("No se encuentra el usuario solicitado o no existe");
                }

                // Crear el objeto ProyectoUsuario
                ProyectoUsuario proyectoUsuario = new ProyectoUsuario();
                proyectoUsuario.setUsuarioId(usuario.getId());
                proyectoUsuario.setUsuarioNombre(usuario.getNombre());
                proyectoUsuario.setUsuarioEmail(usuario.getEmail());

                // Asignar el usuario al proyecto (relación OneToOne)
                proyecto.setProyectoUsuario(proyectoUsuario);
                proyectoRepository.save(proyecto);

                return Optional.of(proyectoUsuario);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteProyectoUsuario(Long proyectoId, Long usuarioId) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(proyectoId);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();

            // Validar si el usuario asignado es el correcto
            if (proyecto.getProyectoUsuario() != null && proyecto.getProyectoUsuario().getUsuarioId().equals(usuarioId)) {
                // Eliminar la relación, ya que es OneToOne
                proyecto.setProyectoUsuario(null);
                proyectoRepository.save(proyecto);
            }
        }
    }

    public Optional<UsuarioDTO> findUsuarioByProyectoId(Long proyectoId) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(proyectoId);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();

            // Obtener el ProyectoUsuario asignado
            ProyectoUsuario proyectoUsuario = proyecto.getProyectoUsuario();
            if (proyectoUsuario != null) {
                // Llamada al microservicio de usuarios para obtener los detalles del usuario
                UsuarioDTO usuario = restTemplate.getForObject("http://localhost:8002/api/usuarios/" + proyectoUsuario.getUsuarioId(), UsuarioDTO.class);
                return Optional.ofNullable(usuario);
            }
        }
        return Optional.empty();  // Si no tiene ningún usuario asignado
    }

    public List<Proyecto> findProyectosByUsuarioId(Long usuarioId) {
        return proyectoRepository.findProyectosByUsuarioId(usuarioId);
    }


}