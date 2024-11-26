package com.projet_iwa.ms_notification.service;

import com.projet_iwa.ms_notification.model.PushNotification;
import com.projet_iwa.ms_notification.repository.PushNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    @Autowired
    private PushNotificationRepository pushNotificationRepository;

    // Méthode pour enregistrer un push token pour un utilisateur
    public void savePushToken(Long idUser, String token) {
        // Vérifier si un token existe déjà pour cet utilisateur
        PushNotification existingPushNotification = pushNotificationRepository.findById(idUser).orElse(null);

        if (existingPushNotification != null) {
            // Si le token existe déjà pour cet utilisateur, le mettre à jour
            existingPushNotification.setToken(token);
            pushNotificationRepository.save(existingPushNotification);
        } else {
            // Si aucun token n'existe, créer un nouveau PushNotification
            PushNotification pushNotification = new PushNotification();
            pushNotification.setIdUser(idUser);
            pushNotification.setToken(token);
            pushNotificationRepository.save(pushNotification);
        }
    }

    // Méthode pour supprimer un push token pour un utilisateur
    public void deletePushToken(Long idUser) {
        PushNotification existingPushNotification = pushNotificationRepository.findById(idUser).orElse(null);

        if (existingPushNotification != null) {
            // Si le token existe, supprimer l'entrée
            pushNotificationRepository.deleteById(idUser);
        } else {
            // Si aucun token n'est trouvé pour cet utilisateur, lancer une exception ou simplement ne rien faire
            throw new IllegalArgumentException("No push token found for the given user ID.");
        }
    }

}

