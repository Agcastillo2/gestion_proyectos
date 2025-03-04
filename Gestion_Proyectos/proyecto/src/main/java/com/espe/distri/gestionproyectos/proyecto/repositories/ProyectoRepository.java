package com.espe.distri.gestionproyectos.proyecto.repositories;

import com.espe.distri.gestionproyectos.proyecto.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    boolean existsByNombreProyecto(String nombreProyecto); // Verificar si ya existe un proyecto con el mismo nombre
}
