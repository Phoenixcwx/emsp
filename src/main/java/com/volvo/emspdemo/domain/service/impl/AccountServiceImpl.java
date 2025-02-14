package com.volvo.emspdemo.domain.service.impl;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.ResponseWrapper;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import com.volvo.emspdemo.domain.event.CardAssignedToAccountEvent;
import com.volvo.emspdemo.domain.service.AccountService;
import com.volvo.emspdemo.domain.service.CardService;
import com.volvo.emspdemo.dto.PageRequest;
import com.volvo.emspdemo.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CardService cardService;

    public AccountServiceImpl(AccountRepository accountRepository, CardService cardService) {
        this.accountRepository = accountRepository;
        this.cardService = cardService;
    }

    @Override
    public Account create(AccountCreatedEvent event) {
        Account exsistAccount = accountRepository.findByEmail(event.getEmail());
        if (exsistAccount != null) {
            throw new IllegalArgumentException("Account with email " + event.getEmail() + "already exists");
        }

        Account account = Account.createNew(event);
        account = accountRepository.save(account);
        return account;
    }

    @Override
    public Account updateStatus(AccountStatusChangedEvent event) {
        Account account = accountRepository.findById(event.getAccountId()).orElseThrow();
        account = account.updateStatus(event.getStatus());
        account = accountRepository.save(account);
        return account;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Account addCard(CardAssignedToAccountEvent event) {
        // 检查 Card 和 Account 是否存在
        Account account = accountRepository.findByIdWithCards(event.getAccountId()).orElseThrow();
        Optional<Card> cardOptional = cardService.findById(event.getCardId());

        List<Long> bandedIds = account.getCards().stream().map(Card::getId).collect(Collectors.toList());
        if(bandedIds.contains(event.getCardId())) {
            throw new IllegalArgumentException("Card with id " + event.getCardId() + " already banded to account " + event.getAccountId());
        }

        //更新 Card
        Card card =  cardOptional.orElseThrow();
        card.assignCardToAccount(account);
        card = cardService.saveCard(card);

        // 设置关联
        account.addCard(card);
        account = accountRepository.save(account);
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public ResponseWrapper<List<Account>> getAccounts(PageRequest request) {
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
        Page<Account> accounts = null;
        if(request.getUpdateTime() != null) {
            accounts = accountRepository.findAccountByUpdatedAtAfter(request.getUpdateTime(), pageRequest);
        } else {
            accounts = accountRepository.findAll(pageRequest);
        }

        ResponseWrapper.PageMetadata pageMetadata = new ResponseWrapper.PageMetadata();
        pageMetadata.setCurrentPage(request.getPageNumber());
        pageMetadata.setPageSize(accounts.getTotalPages());
        pageMetadata.setTotalPages(accounts.getTotalPages());
        pageMetadata.setTotalItems(accounts.getTotalElements());
        List<Account> dataList = accounts.get().collect(Collectors.toList());

        ResponseWrapper<List<Account>> result = ResponseWrapper.success(dataList, pageMetadata);
        return result;
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
