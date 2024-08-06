package com.example.onlinebank.card_service.repository;

import com.example.onlinebank.card_service.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
}
