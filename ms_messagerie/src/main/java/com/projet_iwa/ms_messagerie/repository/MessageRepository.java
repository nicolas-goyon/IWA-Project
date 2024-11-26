package com.projet_iwa.ms_messagerie.repository;

import com.projet_iwa.ms_messagerie.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
        List<Message> findByIdconversation(Long idconversation);

        @Query("SELECT m FROM Message m WHERE m.idconversation = :id ORDER BY m.date DESC LIMIT 1")
        Message findLastMessageByIdconversation(Long id);

}
