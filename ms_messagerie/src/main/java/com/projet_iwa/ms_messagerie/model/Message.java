package com.projet_iwa.ms_messagerie.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.projet_iwa.ms_messagerie.model.Conversation;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idconversation", nullable = false)
    private Long idconversation;

    @Column(name = "iduser", nullable = false)
    private Long iduser; // Identifiant de l'utilisateur qui a envoyé le message

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime date;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdconversation() {
        return idconversation;
    }

    public void setIdconversation(Long idconversation) {
        this.idconversation = idconversation;
    }
    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
