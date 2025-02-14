package com.volvo.emspdemo.service;

import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.CardStatus;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import com.volvo.emspdemo.domain.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CardServiceTest {

    private final CardService cardService;

    @Autowired
    public CardServiceTest(CardService cardService) {
        this.cardService = cardService;
    }

    @Test
    public void testCreateCard() {
        CardCreatedEvent event = new CardCreatedEvent();
        event.setRfId("001");
        Card card = cardService.createCard(event);

        Card card2 = cardService.findById(card.getId()).get();

        assertEquals("001", card.getRfId());
        assertEquals(card.getRfId(), card2.getRfId());
    }

    @Test
    public void testChangeCardStatus() {
        CardCreatedEvent event = new CardCreatedEvent();
        event.setRfId("002");
        Card card = cardService.createCard(event);

        CardStatusChangedEvent cardStatusChangedEvent = new CardStatusChangedEvent();
        cardStatusChangedEvent.setCardId(card.getId());
        cardStatusChangedEvent.setStatus(CardStatus.DEACTIVATED);
        Card card2 = cardService.updateStatus(cardStatusChangedEvent);
        assertEquals(CardStatus.DEACTIVATED, card2.getStatus());
    }

}
