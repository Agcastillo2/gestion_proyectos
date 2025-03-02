package com.espe.distri.gestionproyectos.usuario.repositories;

import com.espe.distri.gestionproyectos.usuario.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email); // Verificar si ya existe un usuario con el mismo email

    @Query("SELECT o FROM Usuario o JOIN o.usuarioRol oj WHERE oj.rolIdRol = :rolIdRol")
    Optional<Usuario> findUsuarioByRolId(@Param("rolIdRol") Long rolIdRol);
}