package com.example.onlinebank.client_service.entity;

import com.example.onlinebank.account_service.entity.Account;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @ManyToOne
    private Account account;
    @Getter
    private Long accountId;

}
