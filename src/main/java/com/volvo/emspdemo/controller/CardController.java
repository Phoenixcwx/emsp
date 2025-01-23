package com.volvo.emspdemo.controller;

import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.command.AssignCardToAccountCommand;
import com.volvo.emspdemo.domain.command.ChangeCardStatusCommand;
import com.volvo.emspdemo.domain.command.CreateCardCommand;
import com.volvo.emspdemo.domain.mapper.AccountMapper;
import com.volvo.emspdemo.domain.mapper.CardMapper;
import com.volvo.emspdemo.dto.AssignCardToAccountRequest;
import com.volvo.emspdemo.dto.ChangeCardStatusRequest;
import com.volvo.emspdemo.dto.CreateCardRequest;
import com.volvo.emspdemo.repository.CardRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
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

    private final CommandGateway commandGateway;
    private final CardRepository cardRepository;

    public CardController(CommandGateway commandGateway, CardRepository cardRepository) {
        this.commandGateway = commandGateway;
        this.cardRepository = cardRepository;
    }

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CreateCardRequest request) {
        CreateCardCommand command = CardMapper.INSTANCE.fromRequest(request);
        commandGateway.send(command);
        return ResponseEntity.ok("Card created");
    }

    @PutMapping("/status")
    public ResponseEntity<String> changeCardStatus(@RequestBody ChangeCardStatusRequest request) {
        ChangeCardStatusCommand command = CardMapper.INSTANCE.fromRequest(request);
        commandGateway.send(command);
        return ResponseEntity.ok("Card status changed");
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Card>> getCardsByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(cardRepository.findByAccountId(accountId));
    }

    @GetMapping("/rfid/{rfId}")
    public ResponseEntity<Card> getCardByRfId(@PathVariable String rfId) {
        return ResponseEntity.ok(cardRepository.findByRfId(rfId));
    }

    @PutMapping("/account")
    public ResponseEntity<String> assignCardToAccount(@RequestBody AssignCardToAccountRequest request) {
        AssignCardToAccountCommand command = AccountMapper.INSTANCE.fromRequest(request);
        commandGateway.send(command);
        return ResponseEntity.ok("Card assigned to account");
    }
}
