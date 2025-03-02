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
                .route("bckmovilesCard", r -> r.path("/api/cards/**")
                        .uri("http://localhost:8001")
                )
                .route("bckmovilesNotification", r -> r.path("/api/notifications/**")
                        .uri("http://localhost:8002")
                )
                .route("bckmovilesPayment", r -> r.path("/api/payments/**")
                        .uri("http://localhost:8003")
                )
                .route("bckmovilesTransaction", r -> r.path("/api/transactions/**")
                        .uri("http://localhost:8004")
                )
                .route("bckmovilesUser", r -> r.path("/api/users/**")
                        .uri("http://localhost:8005")
                )
                .build();
    }
}

