package com.emsp.account.controller;

import com.volvo.emspdemo.domain.AccountAggregate;
import com.volvo.emspdemo.domain.command.ChangeAccountStatusCommand;
import com.volvo.emspdemo.domain.command.CreateAccountCommand;
import com.volvo.emspdemo.repository.AccountRepository;
import com.volvo.emspdemo.util.ContractIdGenerator;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContractIdGenerator contractIdGenerator;

    @PostMapping
    public String createAccount(@RequestBody CreateAccountCommand command) {
        command.setContractId(contractIdGenerator.generateContractIdWithoutPlaceHolder());
        commandGateway.send(command);
        return "Account created";
    }

    @PutMapping("/{email}/status")
    public String changeAccountStatus(@PathVariable String email, @RequestBody ChangeAccountStatusCommand command) {
        command.setEmail(email);
        commandGateway.send(command);
        return "Account status changed";
    }

    @GetMapping("/{email}")
    public AccountAggregate getAccount(@PathVariable String email) {
        return accountRepository.findByEmail(email);
    }

}