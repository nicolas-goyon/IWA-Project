package com.projet_iwa.ms_messagerie.service;

import com.projet_iwa.ms_messagerie.kafka.KafkaProducerService;
import com.projet_iwa.ms_messagerie.model.Conversation;
import com.projet_iwa.ms_messagerie.model.Message;
import com.projet_iwa.ms_messagerie.repository.ConversationRepository;
import com.projet_iwa.ms_messagerie.repository.MessageRepository;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, MessageRepository messageRepository,KafkaProducerService kafkaProducerService) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation) ;
    }
    public Message createMessage(String authorization, Message message) {
        Message messageSaved = null;
        try {
            Optional<Conversation> conversation = conversationRepository.findById(message.getIdconversation());

            if (conversation.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation introuvable");
            }
            // Dis qui est receiver et sender
            messageSaved = messageRepository.save(message);
            if (messageSaved.getIduser().equals(conversation.get().getIduser1())) {
                kafkaProducerService.sendMessageToNotification(authorization, messageSaved.getIduser(), conversation.get().getIduser2());
            }
            else{
                kafkaProducerService.sendMessageToNotification(authorization,messageSaved.getIduser(), conversation.get().getIduser1());
            }

        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur inattendue", e);
        }
        return messageSaved;
    }

    public List<Conversation> getAllConvByUser(Long id){
        return conversationRepository.findByIduser1OrIduser2(id);
    }

    public List<Message> getAllMessageInConv(Long id){
        return messageRepository.findByIdconversation(id);
    }

    public Message getLastMessageInConv(Long id){
        return messageRepository.findLastMessageByIdconversation(id);
    }
}
