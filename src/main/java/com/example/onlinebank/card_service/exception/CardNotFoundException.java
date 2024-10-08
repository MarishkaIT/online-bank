package com.example.onlinebank.card_service.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() {
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
