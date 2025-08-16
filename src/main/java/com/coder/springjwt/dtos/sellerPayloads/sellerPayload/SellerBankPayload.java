package com.coder.springjwt.dtos.sellerPayloads.sellerPayload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerBankPayload {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String bankName;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String  ifscCode;

}
