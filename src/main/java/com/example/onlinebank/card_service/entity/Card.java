package com.example.onlinebank.card_service.entity;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.client_service.entity.Client;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", columnDefinition = "VARCHAR(255)")
    private CardStatus cardStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", columnDefinition = "VARCHAR(255)")
    private CardType cardType;

    @Column(name = "card_number", length = 255, columnDefinition = "VARCHAR(255)")
    private String cardNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "cvv",length = 255, columnDefinition = "VARCHAR(255)")
    private String cvv;

}
