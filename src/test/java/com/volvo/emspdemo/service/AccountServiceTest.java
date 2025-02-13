package com.volvo.emspdemo.service;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.AccountStatus;
import com.volvo.emspdemo.domain.Card;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import com.volvo.emspdemo.domain.event.CardAssignedToAccountEvent;
import com.volvo.emspdemo.domain.event.CardCreatedEvent;
import com.volvo.emspdemo.domain.service.AccountService;
import com.volvo.emspdemo.domain.service.CardService;
import com.volvo.emspdemo.dto.PageRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AccountServiceTest {

    private final AccountService accountService;
    private final CardService cardService;

    @Autowired
    public AccountServiceTest(AccountService accountService, CardService cardService) {
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @Test
    public void testCreateAccount() {
        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setEmail("test@test.com");
        Account result = accountService.create(event);
        assertEquals("test@test.com", result.getEmail());
        // 再次执行 email 重复会报错
        assertThrows(IllegalArgumentException.class, () -> accountService.create(event));
    }

    @Test
    public void testChangeAccountStatus() {
        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setEmail("test2@test.com");
        Account result = accountService.create(event);

        AccountStatusChangedEvent statusChangedEvent = new AccountStatusChangedEvent();
        statusChangedEvent.setAccountId(result.getId());
        statusChangedEvent.setStatus(AccountStatus.DEACTIVATED);
        Account changedAccount = accountService.updateStatus(statusChangedEvent);
        assertEquals(AccountStatus.DEACTIVATED, changedAccount.getStatus());
    }

    @Test
    public void testAssignCard() {
        CardCreatedEvent cardCreatedEvent = new CardCreatedEvent();
        cardCreatedEvent.setRfId("testRfId");
        Card card = cardService.createCard(cardCreatedEvent);

        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setEmail("test3@test.com");
        Account account = accountService.create(event);

        CardAssignedToAccountEvent cardAssignedToAccountEvent = new CardAssignedToAccountEvent();
        cardAssignedToAccountEvent.setCardId(card.getId());
        cardAssignedToAccountEvent.setAccountId(account.getId());
        cardAssignedToAccountEvent.setContractId(account.getContractId());

        Account account1 = accountService.addCard(cardAssignedToAccountEvent);
        Assertions.assertEquals(1, account1.getCards().size());
        Assertions.assertEquals(card.getId(), account1.getCards().get(0).getId());
    }

    @Test
    public void testGetAccount() {
        LocalDateTime first = LocalDateTime.now();
        for (int i = 0; i < 8; i++) {
            String email = "test" + (i+4) + "@test.com";
            AccountCreatedEvent event = new AccountCreatedEvent();
            event.setEmail(email);
            accountService.create(event);
        }

        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(1);
        pageRequest.setPageSize(100);
        pageRequest.setUpdateTime(first);
        List<Account> results = accountService.getAccounts(pageRequest).getData();
        assertEquals(8, results.size());
    }


}
