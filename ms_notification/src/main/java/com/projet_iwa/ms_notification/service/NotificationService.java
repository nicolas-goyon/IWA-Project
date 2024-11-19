package com.projet_iwa.ms_notification.service;


import com.projet_iwa.ms_notification.model.Notification;
import com.projet_iwa.ms_notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // CREATE ou UPDATE une notification
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // READ : Récupérer une notification par ID
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }


    // READ : Récupérer les notifications par utilisateur
    public List<Notification> getNotificationsByUserId(Integer iduser) {
        return notificationRepository.findByIduser(iduser);
    }

    // DELETE : Supprimer une notification par ID
    public void deleteNotificationById(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Notification not found with ID: " + id);
        }
    }

}

