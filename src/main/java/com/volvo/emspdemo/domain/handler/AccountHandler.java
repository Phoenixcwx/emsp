package com.volvo.emspdemo.domain.handler;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.AccountStatus;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import com.volvo.emspdemo.repository.AccountRepository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.stereotype.Component;

@Component
public class AccountHandler {

    private final Account account;
    private final AccountRepository accountRepository;

    public AccountHandler(Account account, AccountRepository accountRepository) {
        this.account = account;
        this.accountRepository = accountRepository;
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        // check email
        Account exsistAccount = accountRepository.findByEmail(event.getEmail());
        if (exsistAccount != null) {
            throw new IllegalArgumentException("Account with email " + event.getEmail() + "already exists");
        }

        account.setEmail(event.getEmail());
        account.setContractId(event.getContractId());
        account.setStatus(AccountStatus.CREATED);
        accountRepository.save(account);
    }

    @EventSourcingHandler
    public void on(AccountStatusChangedEvent event) {
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

}
