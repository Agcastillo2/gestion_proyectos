package com.espe.distri.gestionproyectos.tarea.repositories;

import com.espe.distri.gestionproyectos.tarea.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    boolean existsByNombreTarea(String nombreTarea); // Verificar si ya existe una tarea con el mismo nombre

    @Query("SELECT o FROM Tarea o JOIN o.tareaUsuario oj WHERE oj.usuarioId = :usuarioId")
    Optional<Tarea> findTareaByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT o FROM Tarea o JOIN o.tareaProyecto oj WHERE oj.proyectoId = :proyectoId")
    Optional<Tarea> findTareaByProyectoId(@Param("proyectoId") Long proyectoId);

}
