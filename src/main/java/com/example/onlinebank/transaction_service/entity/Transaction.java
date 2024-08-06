package com.example.onlinebank.transaction_service.entity;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.payment_service.entity.Payment;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description", length = 255)
    private String description;

    @OneToOne(mappedBy = "transaction")
    private Payment payment;
}
