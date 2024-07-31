package com.example.onlinebank.notification_service.controller;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.notification_service.entity.Notification;
import com.example.onlinebank.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private NotificationService notificationService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/account/{accountId}")
    public Account getAccount(@PathVariable Long accountId) {
        String url = "http://account-service/accounts/{accountId}";
        return restTemplate.getForObject(url, Account.class, accountId);
    }

    @GetMapping
    public List<Notification> getNotificationByClientId(@PathVariable Long clientId) {
        return notificationService.getNotificationsByClientId(clientId);
    }

    @GetMapping("/{id}")
    public Optional<Notification> getNotificationById(@PathVariable Long id) {
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

    @PostMapping("/clients/{clientId}/notifications")
    public void sendNotification(@PathVariable Long clientId, @RequestBody Notification notification) {
        String url = "http://client-service/clients/{clientId}/notifications";
        restTemplate.postForObject(url, notification, Void.class, clientId);
    }
}
