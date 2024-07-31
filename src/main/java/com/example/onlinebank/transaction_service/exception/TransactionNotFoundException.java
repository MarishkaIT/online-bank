package com.example.onlinebank.transaction_service.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String transactionNotFound) {
    }
}
