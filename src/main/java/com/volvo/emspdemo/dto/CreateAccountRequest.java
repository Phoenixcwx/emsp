package com.volvo.emspdemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateAccountRequest {
    @NotNull(message = "id can not be null")
    private Long id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Contract ID cannot be null")
    @Pattern(regexp = "[a-z]{2}[\\da-z]{3}[\\da-z]{9}", message = "Contract ID must be in EMAID format")
    private String contractId;
}
