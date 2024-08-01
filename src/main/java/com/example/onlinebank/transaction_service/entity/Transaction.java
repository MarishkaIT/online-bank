package com.example.onlinebank.transaction_service.entity;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.payment_service.entity.Payment;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private Date transactionDate;

    private BigDecimal amount;

    private String description;

    @OneToOne(mappedBy = "transaction")
    private Payment payment;
}
