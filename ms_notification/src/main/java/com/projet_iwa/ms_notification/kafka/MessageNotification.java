package com.projet_iwa.ms_notification.kafka;

public class MessageNotification {
    private String senderId;
    private String receiverId;
    private String message;

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    private String nameSender;

    public String getLastnameSender() {
        return lastnameSender;
    }

    public void setLastnameSender(String lastnameSender) {
        this.lastnameSender = lastnameSender;
    }

    private String lastnameSender;

    // Getters et Setters
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

