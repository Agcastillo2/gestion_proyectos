package com.espe.distri.gestionproyectos.rol.controllers;

import com.espe.distri.gestionproyectos.rol.models.Rol;
import com.espe.distri.gestionproyectos.rol.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<?> createRol(@RequestBody Rol rol) {
        // Verificar si el nombre del rol ya existe antes de guardarlo
        if (rolService.existsByNombreRol(rol.getNombreRol())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El nombre del rol ya está en uso"));
        }

        // Guardar el rol si no está duplicado
        Rol savedRol = rolService.saveRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        Optional<Rol> rol = rolService.getRolById(id);
        return rol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{nombreRol}")
    public ResponseEntity<Boolean> existsByNombreRol(@PathVariable String nombreRol) {
        boolean exists = rolService.existsByNombreRol(nombreRol);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Método para modificar un rol
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarRol(@PathVariable Long id, @RequestBody Rol rol) {
        // Verificar si el nombre del rol ya existe antes de modificarlo
        if (rolService.existsByNombreRol(rol.getNombreRol()) &&
                rolService.getRolById(id).map(existingRol -> !existingRol.getNombreRol().equals(rol.getNombreRol())).orElse(false)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El nombre del rol ya está en uso"));
        }

        // Modificar el rol si no hay conflictos
        Rol rolModificado = rolService.modificarRol(id, rol);

        if (rolModificado != null) {
            return ResponseEntity.ok(rolModificado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Rol no encontrado"));
        }
    }
}
