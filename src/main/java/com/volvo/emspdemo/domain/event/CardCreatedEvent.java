package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardCreatedEvent {
    private Long cardId;
    private String rfId;
    private String contractId;
}
