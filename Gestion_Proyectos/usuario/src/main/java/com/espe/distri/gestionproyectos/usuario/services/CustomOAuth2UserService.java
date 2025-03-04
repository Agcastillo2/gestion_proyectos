package com.espe.distri.gestionproyectos.usuario.services;

import com.espe.distri.gestionproyectos.usuario.models.CustomOAuth2User;
import com.espe.distri.gestionproyectos.usuario.models.Usuario;
import com.espe.distri.gestionproyectos.usuario.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        if (!usuarioService.existsByEmail(email)) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(name);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setContraseña("hashed_password_from_oauth"); // Contraseña ficticia
            //nuevoUsuario.setOauthProvider("github");

            usuarioService.saveUsuario(nuevoUsuario);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomOAuth2User(authorities, oauth2User.getAttributes(), "name");
    }
}