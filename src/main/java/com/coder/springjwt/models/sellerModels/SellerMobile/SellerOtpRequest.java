package com.coder.springjwt.models.sellerModels.SellerMobile;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerOtpRequest {

    @NotBlank
    private String otp;

    @NotBlank
    private String mobile;
}
