package com.espe.distri.gestionproyectos.completado.controllers;

import com.espe.distri.gestionproyectos.completado.models.Completado;
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
}