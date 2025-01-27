package com.volvo.emspdemo.domain.command;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CreateCardCommand {
    @TargetAggregateIdentifier
    private Long cardId;
    private String rfId;
    @Pattern(regexp = "[a-z]{2}[\\da-z]{3}[\\da-z]{9}", message = "Contract ID must be in EMAID format")
    private String contractId;
}
