package com.example.onlinebank.payment_service.service;

import com.example.onlinebank.payment_service.entity.Payment;
import com.example.onlinebank.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    PaymentRepository paymentRepository;


    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getPayment(Long id) {
        return paymentRepository.findById(id).orElseThrow();
    }

    public Payment updatePaymentStatus(Long id, String status) {
        Payment payment = getPayment(id);
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
