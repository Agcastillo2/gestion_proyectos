package com.espe.distri.gestionproyectos.usuario.controllers;

import com.espe.distri.gestionproyectos.usuario.models.DTO.RolDTO;
import com.espe.distri.gestionproyectos.usuario.models.Usuario;
import com.espe.distri.gestionproyectos.usuario.models.relacion.UsuarioRol;
import com.espe.distri.gestionproyectos.usuario.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        // Verificar si el email ya existe antes de guardarlo
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El correo electrónico ya está en uso"));
        }

        // Guardar el usuario si no está duplicado
        Usuario savedUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = usuarioService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Método para modificar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Verificar si el email ya está en uso antes de modificarlo
        if (usuarioService.existsByEmail(usuario.getEmail()) &&
                usuarioService.getUsuarioById(id).map(existingUsuario -> !existingUsuario.getEmail().equals(usuario.getEmail())).orElse(false)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El correo electrónico ya está en uso"));
        }

        // Modificar el usuario si no hay conflictos
        Usuario usuarioModificado = usuarioService.modificarUsuario(id, usuario);

        if (usuarioModificado != null) {
            return ResponseEntity.ok(usuarioModificado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));
        }
    }

    @PostMapping("/{usuarioId}/rolesOf/{rolId}")
    public ResponseEntity<UsuarioRol> assignRol(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        Optional<UsuarioRol> rolAssigned = usuarioService.assignRol(usuarioId, rolId);
        if (rolAssigned.isPresent()) {
            return ResponseEntity.ok(rolAssigned.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{usuarioId}/rolesOf/{rolId}")
    public ResponseEntity<Void> deleteUsuarioRol(@PathVariable Long usuarioId, @PathVariable Long rolId) {
        usuarioService.deleteUsuarioRol(usuarioId, rolId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rolesOf/{rolId}")
    public ResponseEntity<Usuario> findUsuarioByRolId(@PathVariable Long rolId) {
        Optional<Usuario> usuario = usuarioService.findUsuarioByRolId(rolId);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{usuarioId}/rolesOf")
    public ResponseEntity<RolDTO> findRolByUsuarioId(@PathVariable Long usuarioId) {
        Optional<RolDTO> rol = usuarioService.findRolByUsuarioId(usuarioId);
        if (rol.isPresent()) {
            return ResponseEntity.ok(rol.get());
        }
        return ResponseEntity.notFound().build();
    }

}
