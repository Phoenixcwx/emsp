package com.volvo.emspdemo.domain.event;

import com.volvo.emspdemo.domain.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountStatusChangedEvent {
    private String email;
    private AccountStatus status;
}
