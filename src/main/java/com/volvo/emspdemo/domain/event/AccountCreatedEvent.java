package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreatedEvent {
    private Long accountId;
    private String email;
    private String contractId;
}
