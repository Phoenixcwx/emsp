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
    private Long id;
    private String email;
    @Pattern(regexp = "[A-Z]{2}[\\dA-Z]{3}[\\dA-Z]{9}", message = "Contract ID must be in EMAID format")
    private String contractId;
}
