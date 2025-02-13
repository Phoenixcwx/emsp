package com.volvo.emspdemo.controller;

import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.ResponseWrapper;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import com.volvo.emspdemo.domain.mapper.CardMapper;
import com.volvo.emspdemo.domain.service.CardService;
import com.volvo.emspdemo.dto.ChangeCardStatusRequest;
import com.volvo.emspdemo.dto.CreateCardRequest;
import com.volvo.emspdemo.dto.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseWrapper<Card> createCard(@RequestBody CreateCardRequest request) {
        CardCreatedEvent event = CardMapper.INSTANCE.fromRequest(request);
        Card card = cardService.createCard(event);
        return ResponseWrapper.success(card);
    }

    @PutMapping("/status")
    public ResponseWrapper<Card> changeCardStatus(@RequestBody ChangeCardStatusRequest request) {
        CardStatusChangedEvent event = CardMapper.INSTANCE.fromRequest(request);
        Card card = cardService.updateStatus(event);
        return ResponseWrapper.success(card);
    }

    @GetMapping("/account/{accountId}")
    public ResponseWrapper<List<Card>> getCardsByAccountId(@PathVariable Long accountId) {
        return ResponseWrapper.success(cardService.findByAccountId(accountId));
    }

    @GetMapping("/rfid/{rfId}")
    public ResponseWrapper<Card> getCardByRfId(@PathVariable String rfId) {
        return ResponseWrapper.success(cardService.findByCardRfid(rfId));
    }

    @GetMapping("/page")
    public ResponseWrapper<List<Card>> getAllAccountPaged(@RequestBody PageRequest request) {
        return cardService.findAll(request);
    }



}
