package com.volvo.emspdemo.controller;

import com.volvo.emspdemo.domain.CardAggregate;
import com.volvo.emspdemo.domain.command.AssignCardToAccountCommand;
import com.volvo.emspdemo.domain.command.ChangeCardStatusCommand;
import com.volvo.emspdemo.domain.command.CreateCardCommand;
import com.volvo.emspdemo.repository.CardRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping
    public String createCard(@RequestBody CreateCardCommand command) {
        commandGateway.send(command);
        return "Card created";
    }

    @PutMapping("/{uid}/assign")
    public String assignCardToAccount(@PathVariable String uid, @RequestBody AssignCardToAccountCommand command) {
        command.setUid(uid);
        commandGateway.send(command);
        return "Card assigned to account";
    }

    @PutMapping("/{uid}/status")
    public String changeCardStatus(@PathVariable String uid, @RequestBody ChangeCardStatusCommand command) {
        command.setUid(uid);
        commandGateway.send(command);
        return "Card status changed";
    }

    @GetMapping("/contract/{contractId}")
    public List<CardAggregate> getCardsByContractId(@PathVariable String contractId) {
        return cardRepository.findByContractId(contractId);
    }

    @GetMapping("/{rfId}")
    public CardAggregate getCardByRfId(@PathVariable String rfId) {
        return cardRepository.findByRfId(rfId);
    }
}
