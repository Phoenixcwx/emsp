package com.volvo.emspdemo.domain.command;

import com.volvo.emspdemo.domain.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ChangeCardStatusCommand {
    @TargetAggregateIdentifier
    private String cardId;
    private CardStatus status;
}
