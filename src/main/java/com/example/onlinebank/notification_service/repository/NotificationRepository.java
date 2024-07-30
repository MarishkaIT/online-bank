package com.example.onlinebank.notification_service.repository;

import com.example.onlinebank.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByClientId(Long clientId);
}
