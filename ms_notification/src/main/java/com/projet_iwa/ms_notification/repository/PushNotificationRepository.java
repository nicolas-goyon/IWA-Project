package com.projet_iwa.ms_notification.repository;

import com.projet_iwa.ms_notification.model.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {
}
