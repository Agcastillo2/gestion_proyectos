package com.espe.distri.gestionproyectos.completado.controllers;

import com.espe.distri.gestionproyectos.completado.models.Completado;
import com.espe.distri.gestionproyectos.completado.models.DTO.TareaDTO;
import com.espe.distri.gestionproyectos.completado.models.relacion.CompletadoTarea;
import com.espe.distri.gestionproyectos.completado.services.CompletadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/completados")
public class CompletadoController {

    @Autowired
    private CompletadoService completadoService;

    @PostMapping
    public ResponseEntity<Completado> createCompletado(@RequestBody Completado completado) {
        Completado savedCompletado = completadoService.saveCompletado(completado);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompletado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Completado> getCompletadoById(@PathVariable Long id) {
        Optional<Completado> completado = completadoService.getCompletadoById(id);
        return completado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Completado>> getAllCompletados() {
        List<Completado> completados = completadoService.getAllCompletados();
        return new ResponseEntity<>(completados, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompletado(@PathVariable Long id) {
        completadoService.deleteCompletado(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Completado> updateCompletado(@PathVariable Long id, @RequestBody Completado nuevoCompletado) {
        Completado completadoActualizado = completadoService.updateCompletado(id, nuevoCompletado);

        if (completadoActualizado != null) {
            return ResponseEntity.ok(completadoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{completadoId}/tareasOf/{tareaId}")
    public ResponseEntity<CompletadoTarea> assignTarea(@PathVariable Long completadoId, @PathVariable Long tareaId) {
        Optional<CompletadoTarea> tareaAssigned = completadoService.assignTarea(completadoId, tareaId);
        if (tareaAssigned.isPresent()) {
            return ResponseEntity.ok(tareaAssigned.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{completadoId}/tareasOf/{tareaId}")
    public ResponseEntity<Void> deleteCompletadoTarea(@PathVariable Long completadoId, @PathVariable Long tareaId) {
        completadoService.deleteCompletadoTarea(completadoId, tareaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tareasOf/{tareaId}")
    public ResponseEntity<Completado> findCompletadoByTareaId(@PathVariable Long tareaId) {
        Optional<Completado> completado = completadoService.findCompletadoByTareaId(tareaId);
        if (completado.isPresent()) {
            return ResponseEntity.ok(completado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{completadoId}/tareasOf")
    public ResponseEntity<TareaDTO> findTareaByCompletadoId(@PathVariable Long completadoId) {
        Optional<TareaDTO> tarea = completadoService.findTareaByCompletadoId(completadoId);
        if (tarea.isPresent()) {
            return ResponseEntity.ok(tarea.get());
        }
        return ResponseEntity.notFound().build();
    }

}