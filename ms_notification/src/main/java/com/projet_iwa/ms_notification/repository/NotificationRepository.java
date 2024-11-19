package com.projet_iwa.ms_notification.repository;


import com.projet_iwa.ms_notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Méthode pour trouver toutes les notifications par utilisateur
    List<Notification> findByIduser(Integer iduser);

    // Méthode pour supprimer toutes les notifications d'un utilisateur
    void deleteByIduser(Integer iduser);
}
