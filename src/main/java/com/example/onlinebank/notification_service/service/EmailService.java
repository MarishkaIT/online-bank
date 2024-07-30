package com.example.onlinebank.notification_service.service;

import com.example.onlinebank.client_service.entity.Client;
import com.example.onlinebank.client_service.repository.ClientRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private ClientRepository clientRepository;

    private JavaMailSender javaMailSender;

    public void sendEmail(Long clientId, String subject, String body) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        String to = client.getEmail();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }
}
