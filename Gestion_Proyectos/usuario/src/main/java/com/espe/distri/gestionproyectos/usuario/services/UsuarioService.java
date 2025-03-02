package com.espe.distri.gestionproyectos.usuario.services;

import com.espe.distri.gestionproyectos.usuario.models.DTO.RolDTO;
import com.espe.distri.gestionproyectos.usuario.models.Usuario;
import com.espe.distri.gestionproyectos.usuario.models.relacion.UsuarioRol;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario saveUsuario(Usuario usuario); // Guardar un usuario
    Optional<Usuario> getUsuarioById(Long id); // Obtener un usuario por su ID
    List<Usuario> getAllUsuarios(); // Obtener todos los usuarios
    void deleteUsuario(Long id); // Eliminar un usuario por su ID
    boolean existsByEmail(String email); // Verificar si ya existe un usuario con el mismo email
    Usuario modificarUsuario(Long id, Usuario nuevoUsuario); // Modificar un usuario

    Optional<UsuarioRol> assignRol(Long usuarioId, Long rolId);
    void deleteUsuarioRol(Long usuarioId, Long rolId);
    Optional<RolDTO> findRolByUsuarioId(Long usuarioId);
    Optional<Usuario> findUsuarioByRolId(Long rolId);
}
