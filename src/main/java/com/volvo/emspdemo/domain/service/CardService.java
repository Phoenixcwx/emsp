package com.volvo.emspdemo.domain.service;

import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.ResponseWrapper;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import com.volvo.emspdemo.dto.PageRequest;

import java.util.List;
import java.util.Optional;

public interface CardService {

    Card createCard(CardCreatedEvent event);

    Card updateStatus(CardStatusChangedEvent event);

    Card saveCard(Card card);

    Optional<Card> findById(Long id);

    ResponseWrapper<List<Card>> getCards(PageRequest pageable);
}
