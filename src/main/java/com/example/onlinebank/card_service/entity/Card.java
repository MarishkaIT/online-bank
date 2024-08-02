package com.example.onlinebank.card_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private String cardNumber;

    private Date expirationDate;

    private String cvv;


}
