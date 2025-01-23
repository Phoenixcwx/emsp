package com.volvo.emspdemo.dto;

import com.volvo.emspdemo.domain.AccountStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAccountStatusRequest {
    @NotNull(message = "id can not be null")
    private Long accountId;
    private AccountStatus status;
}
