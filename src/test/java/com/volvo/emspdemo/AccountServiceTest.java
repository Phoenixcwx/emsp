package com.volvo.emspdemo;

import com.volvo.emspdemo.domain.AccountAggregate;
import com.volvo.emspdemo.domain.AccountStatus;
import com.volvo.emspdemo.domain.command.ChangeAccountStatusCommand;
import com.volvo.emspdemo.domain.command.CreateAccountCommand;
import com.volvo.emspdemo.repository.AccountRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testCreateAccount() {
        String email = "test@example.com";
        CreateAccountCommand command = new CreateAccountCommand();
        command.setEmail(email);
        commandGateway.send(command);

        AccountAggregate account = accountRepository.findByEmail(email);
        assertEquals(email, account.getEmail());
    }

    @Test
    public void testChangeAccountStatus() {
        String email = "test@example.com";
        ChangeAccountStatusCommand command = new ChangeAccountStatusCommand();
        command.setEmail(email);
        command.setStatus(AccountStatus.DEACTIVATED);
        commandGateway.send(command);

        AccountAggregate account = accountRepository.findByEmail(email);
        assertEquals(AccountStatus.DEACTIVATED, account.getStatus());
    }
}
