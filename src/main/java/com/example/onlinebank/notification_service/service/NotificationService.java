package com.example.onlinebank.notification_service.service;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.account_service.service.AccountService;
import com.example.onlinebank.client_service.entity.Client;
import com.example.onlinebank.client_service.service.ClientService;
import com.example.onlinebank.notification_service.entity.Notification;
import com.example.onlinebank.notification_service.entity.NotificationType;
import com.example.onlinebank.notification_service.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NotificationService {

    private NotificationRepository notificationRepository;

    private EmailService emailService;
    private ClientService clientService;
    private AccountService accountService;

    public List<Notification> getNotificationsByClientId(Long clientId) {
        return notificationRepository.findByClientId(clientId);
    }

    public Notification getNotificationById(Long id)  {
        return notificationRepository.findById(id).orElseThrow();
    }

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public void sendNotification(Notification notification) {
        Client client = clientService.getClientById(notification.getClientId());
        Account account = accountService.getAccountById(client.getAccountId());

        switch (notification.getNotificationType()) {
            case TRANSACTION_SUCCESS:
                sendTransactionSuccessNotification(client, account, notification.getMessage());
                break;
            case TRANSACTION_FAILED:
                sendTransactionFailedNotification(client, account, notification.getMessage());
                break;

            case ACCOUNT_UPDATE:
                sendAccountUpdateNotification(client, account, notification.getMessage());
                break;

            case PASSWORD_RESET:
                sendPasswordResetNotification(client, notification.getMessage());
                break;

            default:
                log.warn("Unknown notification type: {}", notification.getNotificationType());
        }
    }

    private void sendPasswordResetNotification(Client client, String message) {
        String emailSubject = "Password Reset";
        String emailBody = "Dear " + client.getFirstName() + client.getLastName() + ",\n\n" +
                "Your password has been reset.\n\n" +
                message + "\n\n" +
                "Best regards,\n" +
                "Bank Name";

        emailService.sendEmail(client.getId(), emailSubject, emailBody);
    }

    private void sendAccountUpdateNotification(Client client, Account account, String message) {
        String emailSubject = "Account Update";
        String emailBody = "Dear " + client.getFirstName() + client.getLastName() + ",\n\n" +
                "Your account information has been updated.\n\n" +
                message + "\n\n" +
                "Best regards,\n" +
                "Bank Name";

        emailService.sendEmail(client.getId(), emailSubject, emailBody);
    }

    private void sendTransactionFailedNotification(Client client, Account account, String message) {
        String emailSubject = "Transaction Failed";
        String emailBody = "Dear " + client.getFirstName() + client.getLastName() + ",\n\n" +
                "Your transaction of " + account.getCurrency() + " " + account.getBalance() + " has failed.\n\n" +
                message + "\n\n" +
                "Best regards,\n" +
                "Bank Name";
        emailService.sendEmail(client.getId(), emailSubject, emailBody);
    }

    private void sendTransactionSuccessNotification(Client client, Account account, String message) {
        String emailSubject = "Transaction Successful";
        String emailBody = "Dear " + client.getFirstName() + client.getLastName() + "!\n\n" +
                "Your transaction of " + account.getCurrency() + " " + account.getBalance() + " has been successful.\n\n" +
                message + "\n\n" +
                "Best regards, \n" +
                "Bank Test";
        emailService.sendEmail(client.getId(), emailSubject, emailBody);
    }
}
