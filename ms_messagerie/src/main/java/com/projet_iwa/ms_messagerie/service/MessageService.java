package com.projet_iwa.ms_messagerie.service;

import com.projet_iwa.ms_messagerie.kafka.KafkaMessageProducer;
import com.projet_iwa.ms_messagerie.model.Conversation;
import com.projet_iwa.ms_messagerie.model.Message;
import com.projet_iwa.ms_messagerie.repository.ConversationRepository;
import com.projet_iwa.ms_messagerie.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private KafkaMessageProducer kafkaProducer;

    @Autowired
    private ConversationRepository conversationRepository;

    public Message sendMessage(Message message) {
        // Charger la conversation depuis la base de données
        Conversation conversation = conversationRepository.findById(message.getIdconversation())
                .orElseThrow(() -> new IllegalArgumentException("Conversation non trouvée : " + message.getIdconversation()));

        // Enregistrer dans la base de données
        Message savedMessage = messageRepository.save(message);

        // Envoyer le message au topic Kafka
        String topic = "conversation-" + message.getIdconversation();
        kafkaProducer.sendMessage(topic, message.getText());

        return savedMessage;
    }


    public List<Message> getMessages(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
}

