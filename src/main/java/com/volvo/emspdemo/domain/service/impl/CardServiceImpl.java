package com.volvo.emspdemo.domain.service.impl;

import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.CardStatus;
import com.volvo.emspdemo.domain.ResponseWrapper;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import com.volvo.emspdemo.domain.service.CardService;
import com.volvo.emspdemo.dto.PageRequest;
import com.volvo.emspdemo.repository.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(CardCreatedEvent event) {
        Card card = Card.createNew(event.getRfId(), CardStatus.CREATED);
        card = cardRepository.save(card);
        return card;
    }

    @Override
    public Card updateStatus(CardStatusChangedEvent event) {
        Card card = cardRepository.findById(event.getCardId()).orElseThrow();
        card.changeState(event.getStatus());
        card = cardRepository.save(card);
        return card;
    }

    @Override
    public Card saveCard(Card card) {
        card = cardRepository.save(card);
        return card;
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public ResponseWrapper<List<Card>> getCards(PageRequest request) {
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
        Page<Card> cards = null;
        if(request.getUpdateTime() != null) {
            cards = cardRepository.findCardByUpdatedAtAfter(request.getUpdateTime(), pageRequest);
        } else {
            cards = cardRepository.findAll(pageRequest);
        }

        ResponseWrapper.PageMetadata pageMetadata = new ResponseWrapper.PageMetadata();
        pageMetadata.setCurrentPage(cards.getNumber());
        pageMetadata.setPageSize(cards.getTotalPages());
        pageMetadata.setTotalPages(cards.getTotalPages());
        pageMetadata.setTotalItems(cards.getTotalElements());
        List<Card> result = cards.get().collect(Collectors.toList());
        return ResponseWrapper.success(result, pageMetadata);
    }

}
