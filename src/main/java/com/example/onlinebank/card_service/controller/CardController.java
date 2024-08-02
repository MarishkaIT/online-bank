package com.example.onlinebank.card_service.controller;

import com.example.onlinebank.card_service.entity.Card;
import com.example.onlinebank.card_service.servise.CardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    CardService cardService;

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{cardId}")
    public Card getCard(@PathVariable Long cardId) {
        return cardService.getCard(cardId);
    }

    @PostMapping
    public Card createcard(@RequestBody Card card) {
        return cardService.createCard(card);
    }

    @PutMapping("/{cardId}")
    public Card updateCard(@PathVariable Long cardId, @RequestBody Card card) {
        return cardService.updateCard(cardId, card);
    }

    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
    }
}
