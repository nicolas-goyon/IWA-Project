package com.projet_iwa.ms_reservation;

import com.projet_iwa.ms_reservation.dto.Notification;
import com.projet_iwa.ms_reservation.model.Grade;
import com.projet_iwa.ms_reservation.model.Reservation;
import com.projet_iwa.ms_reservation.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
public class Util {
    private static RestTemplate restTemplate;

    @Autowired
    public Util(RestTemplate restTemplate) {
        Util.restTemplate = restTemplate;
    }
    public static boolean datesOverlap(Reservation existingReservation, Reservation newReservation) {
        return !existingReservation.getDateEnd().isBefore(newReservation.getDateStart())
                && !existingReservation.getDateStart().isAfter(newReservation.getDateEnd());
    }

    public static Grade calculateGrade(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return new Grade(0, 0); // Aucun avis, renvoie un grade avec 0 de count et 0 de grade
        }

        // Calculer la somme des notes
        float totalGrade = 0;
        for (Review review : reviews) {
            totalGrade += review.getGrade();
        }

        // Calculer la moyenne des notes
        float averageGrade = totalGrade / reviews.size();

        // Retourner un objet Grade avec le nombre d'avis et la note moyenne
        return new Grade(reviews.size(), averageGrade);
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
    public static void createNotification(String authorizationHeader, Notification notification){
        String jwtToken = Util.extractJwtFromHeader(authorizationHeader);
        String notifIUrl = "http://localhost:8080" + "/notifications";
        System.out.println(notifIUrl);
        ResponseEntity<Notification> response = Util.sendRequestWithJwt(notifIUrl, HttpMethod.POST, jwtToken,Notification.class, notification);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalArgumentException("Échec de la création de la notification. Réponse : "
                    + response.getStatusCode());
        }
        response.getBody();
    }
}
