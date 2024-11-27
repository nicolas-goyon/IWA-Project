package com.projet_iwa.ms_messagerie.kafka;


import com.projet_iwa.ms_messagerie.Util;
import com.projet_iwa.ms_messagerie.model.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpMethod;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    // Use the field directly
    public String getApiGatewayUrl() {
        return apiGatewayUrl;
    }
    public UserDTO getNames(String authorizationHeader, Long id){
        String jwtToken = Util.extractJwtFromHeader(authorizationHeader);

        String userUrl = getApiGatewayUrl() + "/users/" + id;
        ResponseEntity<UserDTO> response = Util.sendRequestWithJwt(userUrl, HttpMethod.GET, jwtToken,UserDTO.class, null);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalArgumentException("Aucun user avec l'ID: " + id + " a été trouvé.");
        }
        return response.getBody();

    }
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToNotification(String authorization, Long senderId, Long receiverId) {
        System.out.println(getApiGatewayUrl());
        String nameSender = getNames(authorization, senderId).getName();
        String lastnameSender = getNames(authorization, senderId).getLastname();
        String notificationMessage = String.format(
                "{\"senderId\": \"%s\", \"receiverId\": \"%s\", \"message\": \"%s\", \"nameSender\": \"%s\", \"lastnameSender\": \"%s\"}",
                senderId, receiverId, String.format("%s a envoyé un message à %d", nameSender, receiverId), nameSender,lastnameSender
        );

        // Envoi du message au topic Kafka
        kafkaTemplate.send("message-notification", notificationMessage);
        System.out.println("Message envoyé à Notification : " + notificationMessage);
    }

}


