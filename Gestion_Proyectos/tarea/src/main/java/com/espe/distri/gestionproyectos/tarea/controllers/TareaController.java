package com.espe.distri.gestionproyectos.tarea.controllers;

import com.espe.distri.gestionproyectos.tarea.models.DTO.ProyectoDTO;
import com.espe.distri.gestionproyectos.tarea.models.DTO.UsuarioDTO;
import com.espe.distri.gestionproyectos.tarea.models.Tarea;
import com.espe.distri.gestionproyectos.tarea.models.relacion.TareaProyecto;
import com.espe.distri.gestionproyectos.tarea.models.relacion.TareaUsuario;
import com.espe.distri.gestionproyectos.tarea.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping
    public ResponseEntity<?> createTarea(@RequestBody Tarea tarea) {
        // Verificar si el nombre de la tarea ya existe antes de guardar
        if (tareaService.existsByNombreTarea(tarea.getNombreTarea())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El nombre de la tarea ya está en uso"));
        }

        // Guardar la tarea si no está duplicada
        Tarea savedTarea = tareaService.saveTarea(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTarea);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTareaById(@PathVariable Long id) {
        Optional<Tarea> tarea = tareaService.getTareaById(id);
        return tarea.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> getAllTareas() {
        List<Tarea> tareas = tareaService.getAllTareas();
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        tareaService.deleteTarea(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{nombreTarea}")
    public ResponseEntity<Boolean> existsByNombreTarea(@PathVariable String nombreTarea) {
        boolean exists = tareaService.existsByNombreTarea(nombreTarea);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTarea(@PathVariable Long id, @RequestBody Tarea nuevaTarea) {
        Tarea tareaUpdate = tareaService.updateTarea(id, nuevaTarea);

        if (tareaUpdate != null) {
            return ResponseEntity.ok(tareaUpdate);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tarea no encontrada"));
        }
    }

    @PostMapping("/{tareaId}/usuariosOf/{usuarioId}")
    public ResponseEntity<TareaUsuario> assignUsuario(@PathVariable Long tareaId, @PathVariable Long usuarioId) {
        Optional<TareaUsuario> usuarioAssigned = tareaService.assignUsuario(tareaId, usuarioId);
        if (usuarioAssigned.isPresent()) {
            return ResponseEntity.ok(usuarioAssigned.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{tareaId}/usuariosOf/{usuarioId}")
    public ResponseEntity<Void> deleteTareaUsuario(@PathVariable Long tareaId, @PathVariable Long usuarioId) {
        tareaService.deleteTareaUsuario(tareaId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuariosOf/{usuarioId}")
    public ResponseEntity<Tarea> findTareaByUsuarioId(@PathVariable Long usuarioId) {
        Optional<Tarea> tarea = tareaService.findTareaByUsuarioId(usuarioId);
        if (tarea.isPresent()) {
            return ResponseEntity.ok(tarea.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{tareaId}/usuariosOf")
    public ResponseEntity<UsuarioDTO> findUsuarioByTareaId(@PathVariable Long tareaId) {
        Optional<UsuarioDTO> usuario = tareaService.findUsuarioByTareaId(tareaId);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{tareaId}/proyectosOf/{proyectoId}")
    public ResponseEntity<TareaProyecto> assignProyecto(@PathVariable Long tareaId, @PathVariable Long proyectoId) {
        Optional<TareaProyecto> proyectoAssigned = tareaService.assignProyecto(tareaId, proyectoId);
        if (proyectoAssigned.isPresent()) {
            return ResponseEntity.ok(proyectoAssigned.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{tareaId}/proyectosOf/{proyectoId}")
    public ResponseEntity<Void> deleteTareaProyecto(@PathVariable Long tareaId, @PathVariable Long proyectoId) {
        tareaService.deleteTareaProyecto(tareaId, proyectoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proyectosOf/{proyectoId}")
    public ResponseEntity<Tarea> findTareaByProyectoId(@PathVariable Long proyectoId) {
        Optional<Tarea> tarea = tareaService.findTareaByProyectoId(proyectoId);
        if (tarea.isPresent()) {
            return ResponseEntity.ok(tarea.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{tareaId}/proyectosOf")
    public ResponseEntity<ProyectoDTO> findProyectoByTareaId(@PathVariable Long tareaId) {
        Optional<ProyectoDTO> proyecto = tareaService.findProyectoByTareaId(tareaId);
        if (proyecto.isPresent()) {
            return ResponseEntity.ok(proyecto.get());
        }
        return ResponseEntity.notFound().build();
    }


}