package com.volvo.emspdemo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCardRequest {
    private String rfId;
    @Pattern(regexp = "[a-z]{2}[\\da-z]{3}[\\da-z]{9}", message = "Contract ID must be in EMAID format")
    private String contractId;
}