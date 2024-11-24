package com.projet_iwa.ms_notification.service;


import com.projet_iwa.ms_notification.controller.PushNotificationController;
import com.projet_iwa.ms_notification.model.Notification;
import com.projet_iwa.ms_notification.model.PushNotification;
import com.projet_iwa.ms_notification.model.PushNotificationRequest;
import com.projet_iwa.ms_notification.repository.NotificationRepository;
import com.projet_iwa.ms_notification.repository.PushNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private PushNotificationRepository pushNotificationRepository;
    @Autowired
    private PushNotificationController pushNotificationController;

    // CREATE ou UPDATE une notification
    @Autowired
    private PushNotificationService pushNotificationService;  // Service pour l'envoi des notifications push

    public Notification saveNotification(Notification notification) {
        Long userId = notification.getIduser();
        Optional<PushNotification> exist = pushNotificationRepository.findById(userId);
        if ( exist.isPresent() ) {
            // Récupération du token
            String token = exist.get().getToken();
            PushNotificationRequest request = new PushNotificationRequest();
            request.setToken(token);
            request.setBody(notification.getBody());
            request.setTitle(notification.getTitle());
            request.setExtraData(notification.getRedirection());

            pushNotificationController.sendPushNotification(request);

        }

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

