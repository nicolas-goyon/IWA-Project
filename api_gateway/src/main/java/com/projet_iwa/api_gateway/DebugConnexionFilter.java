package com.projet_iwa.api_gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;




@Component
public class DebugConnexionFilter implements GatewayFilter {

    private String usersServiceUri;
    private String authRouteUri;
    private String locationsServiceUri;
    private String categoryServiceUri;
    private String reservationServiceUri;
    private String reviewServiceUri;
    private String notificationServiceUri;


    public DebugConnexionFilter(
        @Value("${routes.users-service.uri}") String usersServiceUri,
        @Value("${routes.auth-route.uri}") String authRouteUri,
        @Value("${routes.locations-service.uri}") String locationsServiceUri,
        @Value("${routes.category-service.uri}") String categoryServiceUri,
        @Value("${routes.reservation-service.uri}") String reservationServiceUri,
        @Value("${routes.review-service.uri}") String reviewServiceUri,
        @Value("${routes.notification-service.uri}") String notificationServiceUri
    ) {
        this.usersServiceUri = usersServiceUri;
        this.authRouteUri = authRouteUri;
        this.locationsServiceUri = locationsServiceUri;
        this.categoryServiceUri = categoryServiceUri;
        this.reservationServiceUri = reservationServiceUri;
        this.reviewServiceUri = reviewServiceUri;
        this.notificationServiceUri = notificationServiceUri;
    }


    private boolean checkServiceConnected(String uri) {
        try {
            System.out.println("Checking connection to: " + uri);
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            System.out.println("=> Connection established with: " + uri + " (code: " + code + ")");
        } catch (Exception e) {
            System.out.println("=> Error while connecting to: " + uri);
            System.out.println(e);
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
        // allConnected &= checkServiceConnected(categoryServiceUri);
        allConnected &= checkServiceConnected(reservationServiceUri);
        // allConnected &= checkServiceConnected(reviewServiceUri);
        allConnected &= checkServiceConnected(notificationServiceUri);

        if (!allConnected) {
            System.out.println("=> Error while connecting to one or more services");
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }

        
        // Finir la chaine et renvoyer 200
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return exchange.getResponse().setComplete();
    }
}


