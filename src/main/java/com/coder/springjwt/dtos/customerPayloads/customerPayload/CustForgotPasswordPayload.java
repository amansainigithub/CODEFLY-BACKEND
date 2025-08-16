package com.coder.springjwt.dtos.customerPayloads.customerPayload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
    public class CustForgotPasswordPayload  implements Serializable {


    @NotNull
    @NotBlank(message = "Username must not be Blank")
    private String username;


    @NotNull
    @NotBlank(message = "password must not be Blank")
    private String password;


    @NotNull
    @NotBlank(message = "conformPassword must not be Blank")
    private String conformPassword;


    private String otp;

}
