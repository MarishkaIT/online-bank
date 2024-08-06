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

    private String accountNumber;

    private String accountHolderName;

    private BigDecimal balance;

    private AccountType accountType;

    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
