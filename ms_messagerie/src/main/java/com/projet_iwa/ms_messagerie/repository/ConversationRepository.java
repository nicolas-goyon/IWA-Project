package com.projet_iwa.ms_messagerie.repository;

import com.projet_iwa.ms_messagerie.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}

