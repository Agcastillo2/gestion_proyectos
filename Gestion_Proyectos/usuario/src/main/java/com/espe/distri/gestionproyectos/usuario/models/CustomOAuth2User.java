package com.espe.distri.gestionproyectos.usuario.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User extends DefaultOAuth2User {

    public CustomOAuth2User(OAuth2User user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities, user.getAttributes(), "id");
    }

    @Override
    public String getName() {
        return getAttribute("name");
    }

    public String getEmail() {
        return getAttribute("email");
    }
}
