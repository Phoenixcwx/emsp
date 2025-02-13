package com.volvo.emspdemo.domain.event;

import com.volvo.emspdemo.domain.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusChangedEvent {
    private Long accountId;
    private AccountStatus status;
}
