package com.example.onlinebank.card_service.servise;

import com.example.onlinebank.card_service.entity.Card;
import com.example.onlinebank.card_service.exception.CardNotFoundException;
import com.example.onlinebank.card_service.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCard(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    public Card createCard(Card card) {
        if (card == null) {
            throw new CardNotFoundException("Card cannot be null");
        }

        Card newCard = new Card();
        newCard.setClientId(card.getClientId());
        newCard.setCardType(card.getCardType());
        newCard.setCardNumber(card.getCardNumber());
        newCard.setExpirationDate(card.getExpirationDate());
        newCard.setCvv(card.getCvv());
        return cardRepository.save(newCard);
    }

    public Card updateCard(Long cardId, Card updatedCard) {
        Card existingCard = getCard(cardId);
        if (existingCard != null) {
            existingCard.setCardType(updatedCard.getCardType());
            existingCard.setExpirationDate(updatedCard.getExpirationDate());
            existingCard.setCardNumber(updatedCard.getCardNumber());
            existingCard.setCvv(updatedCard.getCvv());
            return cardRepository.save(existingCard);
        } else {
            throw new CardNotFoundException("Card not found");
        }
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }
}
