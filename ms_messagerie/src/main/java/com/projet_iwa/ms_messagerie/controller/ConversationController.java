package com.projet_iwa.ms_messagerie.controller;


import com.projet_iwa.ms_messagerie.model.Conversation;
import com.projet_iwa.ms_messagerie.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messageries/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    // Créer une nouvelle conversation entre deux utilisateurs
    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody Conversation conversation) {
        Conversation createdConversation = conversationService.createConversation(conversation.getIduser1(), conversation.getIduser2());
        return ResponseEntity.ok(createdConversation);
    }
}

