package com.volvo.emspdemo.domain.command;

import com.volvo.emspdemo.domain.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAccountStatusCommand {
    @TargetAggregateIdentifier
    private String email;
    private AccountStatus status;
}
