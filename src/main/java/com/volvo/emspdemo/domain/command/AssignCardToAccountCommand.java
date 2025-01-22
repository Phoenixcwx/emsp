package com.volvo.emspdemo.domain.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class AssignCardToAccountCommand {
    @TargetAggregateIdentifier
    private String email;
    private String uid;
}
