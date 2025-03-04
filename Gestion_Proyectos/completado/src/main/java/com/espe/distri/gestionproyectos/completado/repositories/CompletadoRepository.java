package com.espe.distri.gestionproyectos.completado.repositories;

import com.espe.distri.gestionproyectos.completado.models.Completado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompletadoRepository extends JpaRepository<Completado, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario

    @Query("SELECT o FROM Completado o JOIN o.completadoTarea oj WHERE oj.tareaId = :tareaId")
    Optional<Completado> findCompletadoByTareaId(@Param("tareaId") Long tareaId);

}