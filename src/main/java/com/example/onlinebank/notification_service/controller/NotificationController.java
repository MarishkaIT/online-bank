package com.example.onlinebank.notification_service.controller;

import com.example.onlinebank.notification_service.entity.Notification;
import com.example.onlinebank.notification_service.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotificationByClientId(@PathVariable Long clientId) {
        return notificationService.getNotificationsByClientId(clientId);
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable Long id) {
       return notificationService.getNotificationById(id);
    }

    @PostMapping
    public void saveNotification(@RequestBody Notification notification) {
         notificationService.saveNotification(notification);
    }
    @DeleteMapping("/{id}")
     public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
     }

     @PostMapping("/send")
    public void sendNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(notification);
     }
}
