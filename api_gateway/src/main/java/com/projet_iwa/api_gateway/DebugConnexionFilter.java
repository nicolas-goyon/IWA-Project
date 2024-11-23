package com.projet_iwa.api_gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;



@Component
public class DebugConnexionFilter implements GatewayFilter {

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


    private boolean checkServiceConnected(String uri) {
        try {
            System.out.println("Checking connection to: " + uri);
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            if (code != 200) {
                System.out.println("=> Error while connecting to: " + uri);
                return false;
            }
        } catch (Exception e) {
            System.out.println("=> Error while connecting to: " + uri);
            return false;
        }
        return true;
    }
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Pour chaque route, afficher l'URI de destination, vérifier que la connexion est bien établie, si non, afficher un message d'erreur
        // System.out.println("usersServiceUri: " + usersServiceUri);
        // System.out.println("authRouteUri: " + authRouteUri);
        // System.out.println("locationsServiceUri: " + locationsServiceUri);
        // System.out.println("categoryServiceUri: " + categoryServiceUri);
        // System.out.println("reservationServiceUri: " + reservationServiceUri);
        // System.out.println("reviewServiceUri: " + reviewServiceUri);
        // System.out.println("notificationServiceUri: " + notificationServiceUri);

        // Tester la connexion avec chaque service
        boolean allConnected = true;
        allConnected &= checkServiceConnected(usersServiceUri);
        allConnected &= checkServiceConnected(authRouteUri);
        allConnected &= checkServiceConnected(locationsServiceUri);
        allConnected &= checkServiceConnected(categoryServiceUri);
        allConnected &= checkServiceConnected(reservationServiceUri);
        allConnected &= checkServiceConnected(reviewServiceUri);
        allConnected &= checkServiceConnected(notificationServiceUri);

        if (!allConnected) {
            System.out.println("=> Error while connecting to one or more services");
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }

        
        // Continuer le passage de la requête avec les en-têtes modifiés
        return chain.filter(exchange);
    }
}


