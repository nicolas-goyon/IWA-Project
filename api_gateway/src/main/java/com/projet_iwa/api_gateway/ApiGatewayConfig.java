package com.projet_iwa.api_gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.projet_iwa.api_gateway.JwtAuthentificationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


@Configuration
public class ApiGatewayConfig {


    @Value("${routes.users-service.uri}")
    private String usersServiceUri;

    @Value("${routes.auth-route.uri}")
    private String authRouteUri;

    @Value("${routes.locations-service.uri}")
    private String locationsServiceUri;

    @Value("${routes.category-service.uri}")
    private String categoryServiceUri;

    @Value("${routes.reservation-service.uri}")
    private String reservationServiceUri;

    @Value("${routes.review-service.uri}")
    private String reviewServiceUri;

    @Value("${routes.notification-service.uri}")
    private String notificationServiceUri;

    @Value("${routes.messagerie-service.uri}")
    private String messagerieServiceUri;

    // Bean pour configurer les routes avec Spring Cloud Gateway
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // debug routes
        System.out.println("usersServiceUri: " + usersServiceUri);
        System.out.println("authRouteUri: " + authRouteUri);
        System.out.println("locationsServiceUri: " + locationsServiceUri);
        System.out.println("categoryServiceUri: " + categoryServiceUri);
        System.out.println("reservationServiceUri: " + reservationServiceUri);
        System.out.println("reviewServiceUri: " + reviewServiceUri);
        System.out.println("notificationServiceUri: " + notificationServiceUri);
        System.out.println("messagerieServiceUri: " + messagerieServiceUri);
        
        return builder.routes()
                .route("users-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri(usersServiceUri))

                .route("auth-route", r -> r.path("/auth/**")
                        .uri(authRouteUri))

                .route("locations-service", r -> r.path("/locations/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri(locationsServiceUri))

                .route("category-service", r -> r.path("/categories/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri(categoryServiceUri))

                .route("reservation-service", r -> r.path("/reservations/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri(reservationServiceUri))

                .route("review-service", r -> r.path("/reviews/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri(reviewServiceUri))
                .route("notification-service", r -> r.path("/notifications/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri(notificationServiceUri))
                .route("messagerie-service", r -> r.path("/conversations/**")
                        .filters(f -> f.filter(new JwtAuthentificationFilter()))
                        .uri(messagerieServiceUri))
                .build();
    }
}
