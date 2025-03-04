package com.espe.distri.gestionproyectos.completado.services.impl;

import com.espe.distri.gestionproyectos.completado.models.Completado;
import com.espe.distri.gestionproyectos.completado.repositories.CompletadoRepository;
import com.espe.distri.gestionproyectos.completado.services.CompletadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompletadoServiceImpl implements CompletadoService {

    @Autowired
    private CompletadoRepository completadoRepository;

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
}