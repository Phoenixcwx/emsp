package com.volvo.emspdemo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignCardToAccountRequest {
    @NotNull(message = "account id can not be null")
    private Long accountId;
    @NotNull(message = "card id can not be null")
    private Long cardId;
}
