package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardAssignedToAccountEvent {
    private Long cardId;
    private Long accountId;
    private String contractId;
}
