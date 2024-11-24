package com.projet_iwa.ms_messagerie.repository;

import com.projet_iwa.ms_messagerie.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
        List<Message> findByIdconversation(Long idconversation);
}
