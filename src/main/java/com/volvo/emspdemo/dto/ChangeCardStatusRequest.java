package com.volvo.emspdemo.dto;

import com.volvo.emspdemo.domain.CardStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeCardStatusRequest {
    @NotNull(message = "id can not be null")
    private Long cardId;
    private CardStatus status;
}
