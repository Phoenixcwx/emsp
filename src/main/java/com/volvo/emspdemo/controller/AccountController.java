package com.volvo.emspdemo.controller;

import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.command.ChangeAccountStatusCommand;
import com.volvo.emspdemo.domain.command.CreateAccountCommand;
import com.volvo.emspdemo.domain.mapper.AccountMapper;
import com.volvo.emspdemo.dto.ChangeAccountStatusRequest;
import com.volvo.emspdemo.dto.CreateAccountRequest;
import com.volvo.emspdemo.repository.AccountRepository;
import com.volvo.emspdemo.util.ContractIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final CommandGateway commandGateway;
    private final AccountRepository accountRepository;
    private final ContractIdGenerator contractIdGenerator;

    public AccountController(CommandGateway commandGateway, AccountRepository accountRepository, ContractIdGenerator contractIdGenerator) {
        this.commandGateway = commandGateway;
        this.accountRepository = accountRepository;
        this.contractIdGenerator = contractIdGenerator;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest request) {
        CreateAccountCommand command = AccountMapper.INSTANCE.fromRequest(request);
        if(StringUtils.isBlank(command.getContractId())) {
            command.setContractId(contractIdGenerator.generateContractIdWithoutPlaceHolder());
        }
        commandGateway.send(command);
        return ResponseEntity.ok("Account created");
    }

    @PutMapping
    public ResponseEntity<String> changeAccountStatus(@RequestBody ChangeAccountStatusRequest request) {
        ChangeAccountStatusCommand command = AccountMapper.INSTANCE.fromRequest(request);
        commandGateway.send(command);
        return ResponseEntity.ok("Account status changed");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable String email) {
        return ResponseEntity.ok(accountRepository.findByEmail(email));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountRepository.findById(id).orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccount() {
        return ResponseEntity.ok(accountRepository.findAll());
    }

}