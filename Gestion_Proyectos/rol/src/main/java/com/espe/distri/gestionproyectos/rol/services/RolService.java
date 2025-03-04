package com.espe.distri.gestionproyectos.rol.services;

import com.espe.distri.gestionproyectos.rol.models.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    Rol saveRol(Rol rol); // Guardar un rol
    Optional<Rol> getRolById(Long id); // Obtener un rol por su ID
    List<Rol> getAllRoles(); // Obtener todos los roles
    void deleteRol(Long id); // Eliminar un rol por su ID
    boolean existsByNombreRol(String nombreRol); // Verificar si ya existe un rol con el mismo nombre

    Rol updateRol(Long idRol, Rol nuevoRol); // Modificar un rol existente
}
