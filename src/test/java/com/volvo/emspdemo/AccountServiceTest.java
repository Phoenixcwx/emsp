package com.volvo.emspdemo;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.AccountStatus;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import com.volvo.emspdemo.domain.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AccountServiceTest {

    private final AccountService accountService;

    @Autowired
    public AccountServiceTest(AccountService accountService) {
        this.accountService = accountService;
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


}
