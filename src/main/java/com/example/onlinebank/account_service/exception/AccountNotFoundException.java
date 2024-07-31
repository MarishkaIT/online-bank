package com.example.onlinebank.account_service.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountNotFound) {
    }
}
