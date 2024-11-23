package com.projet_iwa.ms_messagerie.controller;

import com.projet_iwa.ms_messagerie.model.Conversation;
import com.projet_iwa.ms_messagerie.model.Message;
import com.projet_iwa.ms_messagerie.repository.ConversationRepository;
import com.projet_iwa.ms_messagerie.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messageries/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ConversationRepository conversationRepository;

    // Envoyer un message dans une conversation
    @PostMapping
    public Message sendMessage(Message message) {
        return messageService.sendMessage(message);
    }

    // Récupérer les messages d'une conversation
    @GetMapping
    public ResponseEntity<List<Message>> getMessages(@RequestParam Long conversationId) {
        List<Message> messages = messageService.getMessages(conversationId);
        return ResponseEntity.ok(messages);
    }
}

