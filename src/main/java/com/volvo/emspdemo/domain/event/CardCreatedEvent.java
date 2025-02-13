package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardCreatedEvent {
    private Long cardId;
    private String rfId;
    private String contractId;
}
