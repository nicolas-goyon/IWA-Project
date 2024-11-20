package com.projet_iwa.ms_messagerie.service;

import com.projet_iwa.ms_messagerie.model.Conversation;
import com.projet_iwa.ms_messagerie.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation createConversation(Long user1Id, Long user2Id) {
        Conversation conversation = new Conversation();
        conversation.setIduser1(user1Id);
        conversation.setIduser2(user2Id);
        return conversationRepository.save(conversation);
    }
}

