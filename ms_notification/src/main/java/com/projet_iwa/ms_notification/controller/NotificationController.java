package com.projet_iwa.ms_notification.controller;


import com.projet_iwa.ms_notification.model.Notification;
import com.projet_iwa.ms_notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.saveNotification(notification);
        return ResponseEntity.ok(savedNotification);
    }

    // READ : Récupérer une notification par ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ : Récupérer les notifications par utilisateur
    @GetMapping("/user/{iduser}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Integer iduser) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(iduser);
        return ResponseEntity.ok(notifications);
    }

    // DELETE : Supprimer une notification par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationById(@PathVariable Long id) {
        try {
            notificationService.deleteNotificationById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
