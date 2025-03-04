package com.espe.distri.gestionproyectos.completado.repositories;

import com.espe.distri.gestionproyectos.completado.models.Completado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletadoRepository extends JpaRepository<Completado, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}