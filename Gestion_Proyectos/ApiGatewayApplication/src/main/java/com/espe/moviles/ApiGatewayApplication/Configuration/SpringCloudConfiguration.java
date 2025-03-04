package com.espe.moviles.ApiGatewayApplication.Configuration;

import com.espe.moviles.ApiGatewayApplication.IpConfig;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfiguration {

        private final String localIp = IpConfig.getLocalIp(); // Obtener la IP dinámica

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("rol", r -> r.path("/api/roles/**").uri("http://" + localIp + ":8001"))
                .route("usuario", r -> r.path("/api/usuarios/**").uri("http://" + localIp + ":8002"))
                .route("proyecto", r -> r.path("/api/proyectos/**").uri("http://" + localIp + ":8003"))
                .route("tarea", r -> r.path("/api/tareas/**").uri("http://" + localIp + ":8004"))
                .route("completado", r -> r.path("/api/completados/**").uri("http://" + localIp + ":8005"))
                .build();
    }
}
