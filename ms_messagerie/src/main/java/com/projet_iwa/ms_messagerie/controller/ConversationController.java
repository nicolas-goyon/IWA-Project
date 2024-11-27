package com.projet_iwa.ms_messagerie.controller;

import com.projet_iwa.ms_messagerie.model.Conversation;
import com.projet_iwa.ms_messagerie.model.Message;
import com.projet_iwa.ms_messagerie.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {


    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    // Use the field directly
    public String getApiGatewayUrl() {
        return apiGatewayUrl;
    }

    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @PostMapping
    public Conversation createConversation(@RequestBody Conversation conversation) {
        return conversationService.createConversation(conversation);
    }
    @PostMapping("/messages")
    public Message createMessage(@RequestHeader("Authorization") String authorization, @RequestBody Message message) {
        // l'id de la conv et du user est dans le body
        message.setDate(LocalDateTime.now());
        return conversationService.createMessage(authorization,message);
    }

    @GetMapping("all-by-user/{id}")
    public List<Conversation> getAllConvByUser(@PathVariable Long id) {
        return conversationService.getAllConvByUser(id);
    }

    @GetMapping("all-by-conv/{id}")
    public List<Message> getAllMessageInConv(@PathVariable Long id) {
        return conversationService.getAllMessageInConv(id);
    }
    @GetMapping("lastmessage/{id}")
    public Message getLastMessageInConv(@PathVariable Long id) {
        return conversationService.getLastMessageInConv(id);
    }




}
