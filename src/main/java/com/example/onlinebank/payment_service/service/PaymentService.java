package com.example.onlinebank.payment_service.service;

import com.example.onlinebank.card_service.entity.Card;
import com.example.onlinebank.card_service.exception.CardNotFoundException;
import com.example.onlinebank.card_service.servise.CardService;
import com.example.onlinebank.notification_service.entity.Notification;
import com.example.onlinebank.notification_service.service.NotificationService;
import com.example.onlinebank.payment_service.entity.Payment;
import com.example.onlinebank.payment_service.entity.PaymentStatus;
import com.example.onlinebank.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    @Autowired
    private CardService cardService;

    private NotificationService notificationService;


    public Payment createPayment(Payment payment) {
        Card card = cardService.getCard(payment.getCard().getId());
        if (!isValidCardForTransaction(card, payment.getAmount())){
            throw new CardNotFoundException("Invalid card or insufficient found");
        }
        Payment savedPayment = paymentRepository.save(payment);
        notificationService.sendNotification(new Notification("Payment created", "Payment created successfully", savedPayment.getCard().getClient().getId()));
        return savedPayment;
    }

    private boolean isValidCardForTransaction(Card card, BigDecimal amount) {
        return cardService.isValid(card)
                && card.getAccount().getBalance().compareTo(amount) >= 0
                &&!cardService.isBlocked(card.getId());
    }

    public Payment getPayment(Long id) {
        return paymentRepository.findById(id).orElseThrow();
    }

    public Payment updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = getPayment(id);
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
