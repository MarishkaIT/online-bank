package com.example.onlinebank.account_service.entity;

import com.example.onlinebank.client_service.entity.Client;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
