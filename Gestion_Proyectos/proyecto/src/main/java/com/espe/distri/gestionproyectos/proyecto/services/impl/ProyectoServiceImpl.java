package com.espe.distri.gestionproyectos.proyecto.services.impl;

import com.espe.distri.gestionproyectos.proyecto.models.Proyecto;
import com.espe.distri.gestionproyectos.proyecto.repositories.ProyectoRepository;
import com.espe.distri.gestionproyectos.proyecto.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

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
}