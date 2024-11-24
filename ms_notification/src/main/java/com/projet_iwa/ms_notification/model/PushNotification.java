package com.projet_iwa.ms_notification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "pushNotification")
public class PushNotification {

    @Id
    @Column(name = "iduser")
    private Long idUser;

    @Column(name = "token", nullable = false)
    private String token;

    // Constructeurs
    public PushNotification() {}

    public PushNotification(Long idUser, String token) {
        this.idUser = idUser;
        this.token = token;
    }

    // Getters et Setters
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
