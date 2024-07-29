package com.example.onlinebank.payment_service.controller;

import com.example.onlinebank.payment_service.entity.Payment;
import com.example.onlinebank.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@RequestBody @Valid Payment payment) {
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

    @PutMapping("/{id}/status")
    public Payment updatePaymentStatus(@PathVariable Long id, @RequestBody String status) {
        return paymentService.updatePaymentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}
