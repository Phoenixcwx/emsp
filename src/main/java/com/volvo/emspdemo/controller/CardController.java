package com.volvo.emspdemo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.ResponseWrapper;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import com.volvo.emspdemo.domain.mapper.CardMapper;
import com.volvo.emspdemo.domain.service.CardService;
import com.volvo.emspdemo.dto.ChangeCardStatusRequest;
import com.volvo.emspdemo.dto.CreateCardRequest;
import com.volvo.emspdemo.dto.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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

    @GetMapping("/page")
    public ResponseWrapper<List<Card>> getCardPaged(@RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam("pageNum") Integer pageNum,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
                                                        @Schema(name = "updateTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-02-14 10:10:10")
                                                       @RequestParam(name = "updateTime", required = false) LocalDateTime updateTime) {
        return cardService.getCards(new PageRequest(pageNum, pageSize, updateTime));
    }



}
