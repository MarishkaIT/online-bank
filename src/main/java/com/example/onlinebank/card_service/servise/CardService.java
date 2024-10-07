package com.example.onlinebank.card_service.servise;

import com.example.onlinebank.card_service.entity.Card;
import com.example.onlinebank.card_service.entity.CardStatus;
import com.example.onlinebank.card_service.exception.BalanceNotFoundException;
import com.example.onlinebank.card_service.exception.CardNotFoundException;
import com.example.onlinebank.card_service.exception.InvalidCardException;
import com.example.onlinebank.card_service.repository.CardRepository;
import com.example.onlinebank.notification_service.entity.Notification;
import com.example.onlinebank.notification_service.service.NotificationService;
import com.example.onlinebank.security_service.EncryptService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CardService {

    CardRepository cardRepository;
    NotificationService notificationService;
    EncryptService encryptService;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCard(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    @SneakyThrows
    public Card createCard(Card card) {
        if (!isValid(card)) {
            throw new InvalidCardException("Card cannot be null");
        }

        Card newCard = new Card();
        setCardProperties(card, newCard);

        SecretKey key = encryptService.getSecretKey();
        byte[] encryptedCardNumber = encryptService.encrypt(card.getCardNumber().getBytes(), key);
        byte[] encryptedCvv = encryptService.encrypt(card.getCvv().getBytes(), key);

        newCard.setCardNumber(Arrays.toString(encryptedCardNumber));
        newCard.setCvv(Arrays.toString(encryptedCvv));


        newCard.setCardStatus(CardStatus.NEW);
        notificationService.sendNotification(new Notification("Card created", "Your card hes created successfully", newCard.getClient().getId()));
        return cardRepository.save(newCard);
    }


    public Card updateCard(Long cardId, Card updatedCard) {
        if (!isValid(updatedCard)) {
            throw new CardNotFoundException("Card cannot be null");
        }
        Card existingCard = getCard(cardId);
        if (existingCard == null){
            throw new CardNotFoundException("Card not found");
        }
        setCardProperties(existingCard, updatedCard);
        return cardRepository.save(existingCard);

    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    public boolean isValid(Card card) {
        if (card.getCardNumber() == null || card.getCardNumber().trim().isEmpty() || card.getCardNumber().length() != 16 || !card.getCardNumber().matches("\\d+")) {
            return false;
        }
        if ((card.getExpirationDate() == null) || card.getExpirationDate().before(Date.from(Instant.now()))){
            return false;
        }
        if (card.getCvv() == null || card.getCvv().trim().isEmpty() || card.getCvv().length() != 3 || !card.getCvv().matches("\\d+")) {
            return false;
        }

        return true;
    }

    public boolean isBlocked(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(()-> new CardNotFoundException("Card not found"));
        card.setCardStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        return true;
    }

    private static void setCardProperties(Card card, Card source) {
        source.setClient(card.getClient());
        source.setCardType(card.getCardType());
        source.setCardNumber(card.getCardNumber());
        source.setExpirationDate(card.getExpirationDate());
        source.setCvv(card.getCvv());
    }

}
