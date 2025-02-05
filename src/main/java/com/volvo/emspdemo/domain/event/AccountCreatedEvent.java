package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreatedEvent {
    private Long accountId;
    private String email;
    private String contractId;
}
