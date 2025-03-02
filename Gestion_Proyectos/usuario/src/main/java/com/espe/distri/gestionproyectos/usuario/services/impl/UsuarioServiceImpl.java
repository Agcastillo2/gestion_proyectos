package com.espe.distri.gestionproyectos.usuario.services.impl;

import com.espe.distri.gestionproyectos.usuario.models.DTO.RolDTO;
import com.espe.distri.gestionproyectos.usuario.models.Usuario;
import com.espe.distri.gestionproyectos.usuario.models.relacion.UsuarioRol;
import com.espe.distri.gestionproyectos.usuario.repositories.UsuarioRepository;
import com.espe.distri.gestionproyectos.usuario.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario); // Guarda un usuario en la base de datos
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id); // Obtiene un usuario por su ID
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll(); // Obtiene todos los usuarios
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id); // Elimina un usuario por su ID
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email); // Verifica si ya existe un usuario con el mismo email
    }

    @Override
    public Usuario modificarUsuario(Long id, Usuario nuevoUsuario) {
        // Verificar si el email ya está en uso (excepto para el usuario actual)
        if (usuarioRepository.existsByEmail(nuevoUsuario.getEmail()) &&
                usuarioRepository.findById(id).map(usuario -> !usuario.getEmail().equals(nuevoUsuario.getEmail())).orElse(false)) {
            throw new IllegalArgumentException("Ya existe un usuario con el correo electrónico " + nuevoUsuario.getEmail());
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombre(nuevoUsuario.getNombre());
            usuario.setEmail(nuevoUsuario.getEmail());
            usuario.setContraseña(nuevoUsuario.getContraseña()); // En un caso real, debes cifrar la contraseña
            return usuarioRepository.save(usuario);
        }

        // Si no existe el usuario con ese ID, devolver null o lanzar una excepción
        return null;
    }

    public Optional<UsuarioRol> assignRol(Long usuarioId, Long rolId) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Validar si el rol ya está asignado
            if (usuario.getUsuarioRol() != null && usuario.getUsuarioRol().getRolIdRol().equals(rolId)) {
                throw new IllegalArgumentException("Este rol ya está asignado a este usuario");
            }

            try {
                // Llamada al microservicio de rol
                RolDTO rol = restTemplate.getForObject("http://localhost:8001/api/roles/" + rolId, RolDTO.class);

                if (rol == null) {
                    throw new IllegalArgumentException("No se encuentra el rol solicitado o no existe");
                }

                // Crear el objeto UsuarioRol
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setRolIdRol(rol.getIdRol());
                usuarioRol.setRolNombreRol(rol.getNombreRol());
                usuarioRol.setRolDescripcion(rol.getDescripcion());

                // Asignar el rol al usuario (relación OneToOne)
                usuario.setUsuarioRol(usuarioRol);
                usuarioRepository.save(usuario);

                return Optional.of(usuarioRol);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteUsuarioRol(Long usuarioId, Long rolId) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Validar si el rol asignado es el correcto
            if (usuario.getUsuarioRol() != null && usuario.getUsuarioRol().getRolIdRol().equals(rolId)) {
                // Eliminar la relación, ya que es OneToOne
                usuario.setUsuarioRol(null);
                usuarioRepository.save(usuario);
            }
        }
    }

    public Optional<RolDTO> findRolByUsuarioId(Long usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Obtener el UsuarioRol asignado
            UsuarioRol usuarioRol = usuario.getUsuarioRol();
            if (usuarioRol != null) {
                // Llamada al microservicio de roles para obtener los detalles del rol
                RolDTO rol = restTemplate.getForObject("http://localhost:8001/api/roles/" + usuarioRol.getRolIdRol(), RolDTO.class);
                return Optional.ofNullable(rol);
            }
        }
        return Optional.empty();  // Si no tiene ningún rol asignado
    }

    public Optional<Usuario> findUsuarioByRolId(Long rolId) {
        return usuarioRepository.findUsuarioByRolId(rolId); // Suponiendo que tienes una consulta personalizada
    }

}
