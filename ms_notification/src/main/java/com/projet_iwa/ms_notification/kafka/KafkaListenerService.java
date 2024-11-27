package com.projet_iwa.ms_notification.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet_iwa.ms_notification.controller.NotificationController;
import com.projet_iwa.ms_notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.projet_iwa.ms_notification.model.Notification;
import com.projet_iwa.ms_notification.repository.NotificationRepository;
@Service
public class KafkaListenerService {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @Autowired
    public KafkaListenerService(NotificationRepository notificationRepository, NotificationService notificationService) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "message-notification", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("Message reçu du topic de messagerie: " + message);

        // Utilisation d'ObjectMapper pour parser le message JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MessageNotification notification_object = objectMapper.readValue(message, MessageNotification.class);

            // Créer une instance de Notification
            Notification notif = new Notification();
            notif.setIduser(Long.parseLong(notification_object.getReceiverId())); //TRANSFORMER EN INT
            notif.setBody("Vous avez un nouveau message de "+ notification_object.getNameSender() +" "+ notification_object.getLastnameSender());
            notif.setTitle("Nouveau Message");
            notif.setRedirection("messagerie");

            Notification notification = notificationService.saveNotification(notif);

            // Sauvegarder la notification dans la base de données
            notificationRepository.save(notification);

            // Logique de traitement supplémentaire si nécessaire (par exemple, envoyer un email ou une notification push)
            System.out.println("Notification créée et sauvegardée: " + notification);


        } catch (Exception e) {
            // Gestion des erreurs de parsing JSON
            System.err.println("Erreur lors du traitement du message Kafka: " + e.getMessage());
        }
    }
}

