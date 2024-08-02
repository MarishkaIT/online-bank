package com.example.onlinebank.card_service.entity;

public enum CardStatus {
    ACTIVE("Active card"),
    BLOCKED("Blocked card"),
    EXPIRED("Expired card"),
    CANCELED("Canceled card"),
    NEW("New card");

    private final String description;

    CardStatus(String description) {
        this.description = description;
    }
}
