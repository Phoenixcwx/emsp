package com.volvo.emspdemo.domain.handler;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.CardStatus;
import com.volvo.emspdemo.domain.event.CardAssignedToAccountEvent;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.event.CardStatusChangedEvent;
import com.volvo.emspdemo.repository.AccountRepository;
import com.volvo.emspdemo.repository.CardRepository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CardHandler {

    private final Card card;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    public CardHandler(Card card, AccountRepository accountRepository, CardRepository cardRepository) {
        this.card = card;
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    @EventSourcingHandler
    public void on(CardCreatedEvent event) {
        card.setStatus(CardStatus.CREATED);
        card.setRfId(event.getRfId());
        cardRepository.save(card);
    }

    @EventSourcingHandler
    public void on(CardAssignedToAccountEvent event) {
        // 检查 Account 是否存在
        Optional<Account> accountOptional = accountRepository.findById(event.getAccountId());
        accountOptional.orElseThrow();
        Account account = accountOptional.get();

        // 设置关联
        card.setStatus(CardStatus.ASSIGNED);
        card.setAccount(account);
        cardRepository.save(card);

        account.getCards().add(card);
        accountRepository.save(account);
    }

    @EventSourcingHandler
    public void on(CardStatusChangedEvent event) {
        card.setStatus(event.getStatus());
        cardRepository.save(card);
    }

}
