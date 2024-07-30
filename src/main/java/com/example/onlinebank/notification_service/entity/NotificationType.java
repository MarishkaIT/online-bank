package com.example.onlinebank.notification_service.entity;

import lombok.Getter;

@Getter
public enum NotificationType {

    TRANSACTION_SUCCESS("Transaction success"),
    TRANSACTION_FAILED("Transaction failed"),
    PASSWORD_RESET("Password reset"),
    ACCOUNT_UPDATE("Account update");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }
}
