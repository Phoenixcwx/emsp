package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardAssignedToAccountEvent {
    private Long cardId;
    private Long accountId;
    private String contractId;
}
