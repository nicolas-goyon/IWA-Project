package com.projet_iwa.ms_messagerie.model;
import jakarta.persistence.*;

@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Utilisation de "id" comme identifiant unique

    @Column(name = "iduser1", nullable = false)
    private Long iduser1; // Premier utilisateur

    @Column(name = "iduser2", nullable = false)
    private Long iduser2; // Second utilisateur

    public Conversation() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIduser1() {
        return iduser1;
    }

    public void setIduser1(Long iduser1) {
        this.iduser1 = iduser1;
    }

    public Long getIduser2() {
        return iduser2;
    }

    public void setIduser2(Long iduser2) {
        this.iduser2 = iduser2;
    }
}