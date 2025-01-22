package com.volvo.emspdemo.domain.command;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCardCommand {
    private String rfId;
    @Pattern(regexp = "[A-Z]{2}[\\dA-Z]{3}[\\dA-Z]{9}", message = "Contract ID must be in EMAID format")
    private String contractId;
}
