package com.coder.springjwt.dtos.customerPayloads.freshUserPayload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyMobileOtpPayload {

    @NotNull
    @NotBlank
    private String username;

    @NotBlank
    @NotNull
    private String mobileOtp;
}
