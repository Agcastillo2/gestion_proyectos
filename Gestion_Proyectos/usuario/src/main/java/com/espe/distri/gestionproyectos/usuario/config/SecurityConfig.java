package com.espe.distri.gestionproyectos.usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Protege rutas de admin
                        .requestMatchers("/user/**").hasRole("USER") // Protege rutas de usuario
                        .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
                )
                .oauth2Login(withDefaults()); // Activa autenticación con GitHub

        return http.build();
    }
}
