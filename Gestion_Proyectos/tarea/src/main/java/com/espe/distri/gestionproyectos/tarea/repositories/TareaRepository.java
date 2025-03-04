package com.espe.distri.gestionproyectos.tarea.repositories;

import com.espe.distri.gestionproyectos.tarea.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    boolean existsByNombreTarea(String nombreTarea); // Verificar si ya existe una tarea con el mismo nombre
}
