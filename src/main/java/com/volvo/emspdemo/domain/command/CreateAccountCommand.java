package com.volvo.emspdemo.domain.command;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountCommand {
    @TargetAggregateIdentifier
    private Long accountId;
    private String email;
    @Pattern(regexp = "[a-z]{2}[\\da-z]{3}[\\da-z]{9}", message = "Contract ID must be in EMAID format")
    private String contractId;
}
