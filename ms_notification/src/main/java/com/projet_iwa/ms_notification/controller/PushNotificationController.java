package com.projet_iwa.ms_notification.controller;

import com.projet_iwa.ms_notification.model.PushNotificationRequest;
import com.projet_iwa.ms_notification.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/notifications/push-notifications")
public class PushNotificationController {
    // URL de l'API Expo Push (pas besoin de clé API en développement)
    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    @Autowired
    private PushNotificationService pushNotificationService;

    // Endpoint pour s'abonner au push token (login)
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribePushToken(
            @RequestHeader("X-User-Id") Long idUser,
            @RequestBody String token) {

        if (idUser == null) {
            return ResponseEntity.badRequest().body("User ID is required in the header.");
        }
        try {
            pushNotificationService.savePushToken(idUser, token);
            return ResponseEntity.ok("Token subscribed successfully.");
        } catch (Exception e) {
            // Gérer l'exception en fonction de ton besoin
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error subscribing push token.");
        }
    }

    // Endpoint pour se désabonner du push token (logout)
    @DeleteMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribePushToken(
            @RequestHeader("X-User-Id") Long idUser) {

        if (idUser == null) {
            return ResponseEntity.badRequest().body("User ID is required in the header.");
        }

        try {
            pushNotificationService.deletePushToken(idUser);
            return ResponseEntity.ok("Token unsubscribed successfully.");
        } catch (Exception e) {
            // Gérer l'exception selon ton besoin
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error unsubscribing push token.");
        }
    }

    // Endpoint pour envoyer la notification
    @PostMapping("/send")
    public ResponseEntity<String> sendPushNotification(@RequestBody PushNotificationRequest request) {
        try {

            String chaine = request.getToken();
            chaine = chaine.substring(1, chaine.length() - 1);
            // Corps de la notification
            String json = "{"
                    + "\"to\": \"" + chaine + "\","
                    + "\"title\": \"" + request.getTitle() + "\","
                    + "\"body\": \"" + request.getBody() + "\","
                    + "\"data\": { \"extraData\": \"" + request.getExtraData() + "\" },"
                    + "\"sound\": \"default\","
                    + "\"priority\": \"high\""
                    + "}";

            // RestTemplate pour envoyer la requête HTTP POST
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");

            // Créer l'entité avec le corps de la requête et les en-têtes
            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            // Envoyer la requête POST vers Expo
            ResponseEntity<String> response = restTemplate.exchange(EXPO_PUSH_URL, HttpMethod.POST, entity, String.class);

            return new ResponseEntity<>("Notification sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to send notification: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
