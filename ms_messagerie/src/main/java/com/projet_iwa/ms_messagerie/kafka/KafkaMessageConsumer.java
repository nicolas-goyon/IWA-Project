package com.projet_iwa.ms_messagerie.kafka;

import com.projet_iwa.ms_messagerie.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageConsumer {

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "conversation-*", groupId = "message-group")
    public void consumeMessage(String message) {
        System.out.println("Message reçu: " + message);
        // Parse le message et enregistre-le en base
        // Exemple : JSON -> Objet Messages
    }
}
