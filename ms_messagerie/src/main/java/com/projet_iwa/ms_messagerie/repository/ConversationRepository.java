package com.projet_iwa.ms_messagerie.repository;


import com.projet_iwa.ms_messagerie.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
        // Utilisation de JPQL pour gérer la condition OR entre iduser1 et iduser2
        @Query("SELECT c FROM Conversation c WHERE c.iduser1 = :iduser OR c.iduser2 = :iduser")
        List<Conversation> findByIduser1OrIduser2(Long iduser);
}
