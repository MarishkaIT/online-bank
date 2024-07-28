package com.example.onlinebank.account_service.entity;

import lombok.Getter;

@Getter
public enum AccountType {

    CHECKING("Checking"),
    SAVINGS("Savings"),
    CREDIT("Credit"),
    INVESTMENT("Investment"),
    LOAN("Loan");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }
}
