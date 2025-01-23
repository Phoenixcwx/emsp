package com.volvo.emspdemo.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CardAssignedToAccountEvent {
    @TargetAggregateIdentifier
    private Long cardId;
    private Long accountId;
    private String contractId;
}
