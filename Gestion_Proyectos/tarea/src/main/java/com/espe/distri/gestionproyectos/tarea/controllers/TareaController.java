package com.espe.distri.gestionproyectos.tarea.controllers;

import com.espe.distri.gestionproyectos.tarea.models.Tarea;
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
}