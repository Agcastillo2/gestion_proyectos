package com.espe.distri.gestionproyectos.rol.repositories;

import com.espe.distri.gestionproyectos.rol.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombreRol(String nombreRol); // Verificar si ya existe un rol con el mismo nombre
}