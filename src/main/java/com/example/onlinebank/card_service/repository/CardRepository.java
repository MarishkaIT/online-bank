package com.example.onlinebank.card_service.repository;

import com.example.onlinebank.card_service.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
