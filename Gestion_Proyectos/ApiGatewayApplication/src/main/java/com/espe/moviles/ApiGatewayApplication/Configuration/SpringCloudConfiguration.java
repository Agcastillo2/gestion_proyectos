package com.espe.moviles.ApiGatewayApplication.Configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfiguration {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("rol", r -> r.path("/api/roles/**")
                        .uri("http://localhost:8001")
                )
                .route("usuario", r -> r.path("/api/usuarios/**")
                        .uri("http://localhost:8002")
                )
                .build();
    }
}

