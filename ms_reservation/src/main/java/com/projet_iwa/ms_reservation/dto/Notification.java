package com.projet_iwa.ms_reservation.dto;

import jakarta.persistence.*;


public class Notification {


    private Long id;
    private String title;
    private Long iduser;
    private String redirection;
    private String body;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getRedirection() {
        return redirection;
    }

    public void setRedirection(String redirection) {
        this.redirection = redirection;
    }

    // Constructeurs

    public Notification() {}

    public Notification(String body, String title, Long iduser, String redirection, String icon) {
        this.body = body;
        this.title = title;
        this.iduser = iduser;
        this.redirection = redirection;
        this.icon = icon;
    }
}
