package com.projet_iwa.ms_messagerie.kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageToNotification(Long senderId, Long receiverId) {
        String notificationMessage = String.format(
                "{\"senderId\": \"%s\", \"receiverId\": \"%s\", \"message\": \"%s\"}",
                senderId.toString(), receiverId.toString(), String.format("%d a envoyé un message à %d", senderId, receiverId)
        );
        // Envoi du message au topic Kafka
        kafkaTemplate.send("message-notification", notificationMessage);
        System.out.println("Message envoyé à Notification : " + notificationMessage);
    }

}


