package com.volvo.emspdemo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.volvo.emspdemo.domain.Account;
import com.volvo.emspdemo.domain.ResponseWrapper;
import com.volvo.emspdemo.domain.event.AccountCreatedEvent;
import com.volvo.emspdemo.domain.event.AccountStatusChangedEvent;
import com.volvo.emspdemo.domain.event.CardAssignedToAccountEvent;
import com.volvo.emspdemo.domain.mapper.AccountMapper;
import com.volvo.emspdemo.domain.service.AccountService;
import com.volvo.emspdemo.dto.AssignCardToAccountRequest;
import com.volvo.emspdemo.dto.ChangeAccountStatusRequest;
import com.volvo.emspdemo.dto.CreateAccountRequest;
import com.volvo.emspdemo.dto.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseWrapper<Account> createAccount(@RequestBody CreateAccountRequest request) {
        AccountCreatedEvent event = AccountMapper.INSTANCE.fromRequest(request);
        Account account = accountService.create(event);
        return ResponseWrapper.success(account);
    }

    @PutMapping
    public ResponseWrapper<Account> changeAccountStatus(@RequestBody ChangeAccountStatusRequest request) {
        AccountStatusChangedEvent event = AccountMapper.INSTANCE.fromRequest(request);
        Account result = accountService.updateStatus(event);
        return ResponseWrapper.success(result);
    }

    @GetMapping("/page")
    public ResponseWrapper<List<Account>> getAllAccountPaged(@RequestParam("pageSize") Integer pageSize,
                                                             @RequestParam("pageNum") Integer pageNum,
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                             @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
                                                             @Schema(name = "updateTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-02-14 10:10:10")
                                                             @RequestParam(name = "updateTime", required = false) LocalDateTime updateTime) {
        ResponseWrapper<List<Account>> results = accountService.getAccounts(new PageRequest(pageNum, pageSize, updateTime));
        return results;
    }

    @PutMapping("/card")
    public ResponseWrapper<Account> assignCardToAccount(@RequestBody AssignCardToAccountRequest request) {
        CardAssignedToAccountEvent event = AccountMapper.INSTANCE.fromRequest(request);
        Account account = accountService.addCard(event);
        return ResponseWrapper.success(account);
    }

}