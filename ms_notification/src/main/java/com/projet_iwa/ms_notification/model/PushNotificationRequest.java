package com.projet_iwa.ms_notification.model;

public class PushNotificationRequest {

    private String token;  // Le push token Expo de l'utilisateur
    private String title;  // Le titre de la notification
    private String body;   // Le corps de la notification
    private String extraData; // Données supplémentaires (facultatif)

    // Getters et setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
