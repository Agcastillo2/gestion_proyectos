package com.espe.distri.gestionproyectos.rol.services.impl;

import com.espe.distri.gestionproyectos.rol.models.Rol;
import com.espe.distri.gestionproyectos.rol.repositories.RolRepository;
import com.espe.distri.gestionproyectos.rol.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol); // Guarda un rol en la base de datos
    }

    @Override
    public Optional<Rol> getRolById(Long id) {
        return rolRepository.findById(id); // Obtiene un rol por su ID
    }

    @Override
    public List<Rol> getAllRoles() {
        return rolRepository.findAll(); // Obtiene todos los roles
    }

    @Override
    public void deleteRol(Long id) {
        rolRepository.deleteById(id); // Elimina un rol por su ID
    }

    @Override
    public boolean existsByNombreRol(String nombreRol) {
        return rolRepository.existsByNombreRol(nombreRol); // Verifica si ya existe un rol con el mismo nombre
    }

    @Override
    public Rol updateRol(Long idRol, Rol nuevoRol) {
        // Verificar si el nombre del rol ya existe (excepto el rol con el idRol actual)
        if (rolRepository.existsByNombreRol(nuevoRol.getNombreRol()) &&
                rolRepository.findById(idRol).map(rol -> !rol.getNombreRol().equals(nuevoRol.getNombreRol())).orElse(true)) {
            throw new IllegalArgumentException("Ya existe un rol con el nombre " + nuevoRol.getNombreRol());
        }

        Optional<Rol> rolExistente = rolRepository.findById(idRol);

        if (rolExistente.isPresent()) {
            Rol rol = rolExistente.get();
            rol.setNombreRol(nuevoRol.getNombreRol());
            rol.setDescripcion(nuevoRol.getDescripcion());
            return rolRepository.save(rol);
        }

        // Si no existe el rol con ese ID, devolver null o lanzar una excepción
        return null;
    }
}
