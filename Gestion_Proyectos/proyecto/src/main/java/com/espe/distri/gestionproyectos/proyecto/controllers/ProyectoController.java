package com.espe.distri.gestionproyectos.proyecto.controllers;

import com.espe.distri.gestionproyectos.proyecto.models.Proyecto;
import com.espe.distri.gestionproyectos.proyecto.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @PostMapping
    public ResponseEntity<?> createProyecto(@RequestBody Proyecto proyecto) {
        // Verificar si el nombre del proyecto ya existe antes de guardar
        if (proyectoService.existsByNombreProyecto(proyecto.getNombreProyecto())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El nombre del proyecto ya está en uso"));
        }

        // Guardar el proyecto si no está duplicado
        Proyecto savedProyecto = proyectoService.saveProyecto(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProyecto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.getProyectoById(id);
        return proyecto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        List<Proyecto> proyectos = proyectoService.getAllProyectos();
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) {
        proyectoService.deleteProyecto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{nombreProyecto}")
    public ResponseEntity<Boolean> existsByNombreProyecto(@PathVariable String nombreProyecto) {
        boolean exists = proyectoService.existsByNombreProyecto(nombreProyecto);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProyecto(@PathVariable Long id, @RequestBody Proyecto nuevoProyecto) {
        Proyecto proyectoUpdate = proyectoService.updateProyecto(id, nuevoProyecto);

        if (proyectoUpdate != null) {
            return ResponseEntity.ok(proyectoUpdate);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Proyecto no encontrado"));
        }
    }
}