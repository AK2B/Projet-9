package com.microservice.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("ui-service", r -> r.path("/ui/**")
                .uri("http://localhost:8080"))  // UI service runs on port 8083
            .route("patient-service", r -> r.path("/patients/**")
                .uri("http://localhost:8082"))  // patient-service runs on port 8080
            .route("notes-service", r -> r.path("/notes/**")
                .uri("http://localhost:8083"))    // notes-service runs on port 8081
            .route("assessment-service", r -> r.path("/assessment/**")
                .uri("http://localhost:8084"))  // assessment-service runs on port 8082
            .build();
    }
}
