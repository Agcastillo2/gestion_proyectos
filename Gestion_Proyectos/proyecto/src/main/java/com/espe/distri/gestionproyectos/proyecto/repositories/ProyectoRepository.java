package com.espe.distri.gestionproyectos.proyecto.repositories;

import com.espe.distri.gestionproyectos.proyecto.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    boolean existsByNombreProyecto(String nombreProyecto); // Verificar si ya existe un proyecto con el mismo nombre

    @Query("SELECT o FROM Proyecto o JOIN o.proyectoUsuario oj WHERE oj.usuarioId = :usuarioId")
    Optional<Proyecto> findProyectoByUsuarioId(@Param("usuarioId") Long usuarioId);

}
