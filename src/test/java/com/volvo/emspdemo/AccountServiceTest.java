package com.volvo.emspdemo;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.AccountStatus;
import com.volvo.emspdemo.domain.command.ChangeAccountStatusCommand;
import com.volvo.emspdemo.domain.command.CreateAccountCommand;
import com.volvo.emspdemo.repository.AccountRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountServiceTest {

    private final CommandGateway commandGateway;
    private final AccountRepository accountRepository;

    public AccountServiceTest(AccountRepository accountRepository, CommandGateway commandGateway) {
        this.accountRepository = accountRepository;
        this.commandGateway = commandGateway;
    }

    @Test
    public void testCreateAccount() {
        String email = "test@example.com";
        CreateAccountCommand command = new CreateAccountCommand();
        command.setEmail(email);
        commandGateway.send(command);

        Account account = accountRepository.findByEmail(email);
        assertEquals(email, account.getEmail());
    }

    @Test
    public void testChangeAccountStatus() {
        String email = "test@example.com";
        CreateAccountCommand createCommand = new CreateAccountCommand();
        createCommand.setEmail(email);
        commandGateway.send(createCommand);

        Account account = accountRepository.findByEmail(email);

        ChangeAccountStatusCommand command = new ChangeAccountStatusCommand();
        command.setAccountId(account.getId());
        command.setStatus(AccountStatus.DEACTIVATED);
        commandGateway.send(command);

        Account account2 = accountRepository.findByEmail(email);
        assertEquals(AccountStatus.DEACTIVATED, account2.getStatus());
    }
}
