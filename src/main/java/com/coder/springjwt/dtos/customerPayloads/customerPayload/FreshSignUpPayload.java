package com.coder.springjwt.dtos.customerPayloads.customerPayload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FreshSignUpPayload {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;


}
