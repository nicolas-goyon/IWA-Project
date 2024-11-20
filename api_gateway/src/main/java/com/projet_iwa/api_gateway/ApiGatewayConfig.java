package com.projet_iwa.api_gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.projet_iwa.api_gateway.JwtAuthentificationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


@Configuration
public class ApiGatewayConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // Bean pour configurer les routes avec Spring Cloud Gateway
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8087"))

                .route("auth-route", r -> r.path("/auth/**")
                        .uri("http://localhost:8087"))

                .route("locations-service", r -> r.path("/locations/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8082"))

                .route("category-service", r -> r.path("/category/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8082"))

                .route("reservation-service", r -> r.path("/reservations/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8083"))

                .route("review-service", r -> r.path("/reviews/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8083"))
                .route("notification-service", r -> r.path("/notifications/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8084"))
                .route("messages-service", r -> r.path("/messages/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8085"))
                .route("conversation-service", r -> r.path("/conversations/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri("http://localhost:8085"))

                .build();
    }
}
