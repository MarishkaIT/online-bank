package com.example.onlinebank.payment_service.entity;

import com.example.onlinebank.transaction_service.entity.Transaction;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Transaction transaction;

    private Long cardId;

    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    private PaymentStatus paymentStatus;
}
