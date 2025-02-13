package com.volvo.emspdemo.domain.service;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.ResponseWrapper;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import com.volvo.emspdemo.domain.event.CardAssignedToAccountEvent;
import com.volvo.emspdemo.dto.PageRequest;

import java.util.List;

public interface AccountService {

    Account create(AccountCreatedEvent event);

    Account updateStatus(AccountStatusChangedEvent event);

    Account addCard(CardAssignedToAccountEvent event);

    List<Account> getAccounts();

    ResponseWrapper<List<Account>> getAccounts(PageRequest pageRequest);

    Account getAccountByEmail(String email);

    Account getAccountById(Long id);
}
