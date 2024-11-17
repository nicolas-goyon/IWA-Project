package com.projet_iwa.ms_location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Util {

    private static RestTemplate restTemplate;

    @Autowired
    public Util(RestTemplate restTemplate) {
        Util.restTemplate = restTemplate;
    }

    // Méthode pour obtenir les dates entre deux dates
    public static List<Date> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<Date> dates = new ArrayList<>();

        // Boucle entre la date de début et la date de fin
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            dates.add(java.sql.Date.valueOf(current));  // Convertir en Date si nécessaire
            current = current.plusDays(1);
        }

        return dates;
    }

    // Méthode pour extraire le JWT depuis l'en-tête Authorization
    public static String extractJwtFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);  // Extraire le token
        }
        return null;  // Retourne null si le token est manquant ou invalide
    }

    // Méthode pour ajouter le JWT aux en-têtes et effectuer la requête sortante
    public static <T> HttpEntity<T> createRequestEntityWithJwt(String jwtToken, T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        return new HttpEntity<>(body, headers);  // Crée une entité avec les headers et le corps
    }

    public static <T> ResponseEntity<T> sendRequestWithJwt(
            String url,
            HttpMethod method,
            String jwtToken,
            Class<T> responseType,  // Type simple comme UserDTO
            Object requestBody) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        // Utilisation de la méthode avec un Class<T> pour le type simple
        return restTemplate.exchange(url, method, entity, responseType);
    }

    public static <T> ResponseEntity<T> sendRequestWithJwt1(
            String url,
            HttpMethod method,
            String jwtToken,
            ParameterizedTypeReference<T> responseType,  // Type paramétré comme List<Reservation>
            Object requestBody) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        // Utilisation de la méthode avec un ParameterizedTypeReference<T> pour les types paramétrés
        return restTemplate.exchange(url, method, entity, responseType);
    }


}
