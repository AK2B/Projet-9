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
            .route("patient-service", r -> r.path("/patients/**")
                .uri("http://apipatient:8082"))  
            .route("notes-service", r -> r.path("/notes/**")
                .uri("http://notes:8083"))    
            .route("assessment-service", r -> r.path("/assessment/**")
                .uri("http://assessment:8084"))  
            .build();
    }
}