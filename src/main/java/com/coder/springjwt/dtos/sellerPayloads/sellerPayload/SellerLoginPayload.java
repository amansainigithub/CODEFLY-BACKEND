package com.coder.springjwt.dtos.sellerPayloads.sellerPayload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerLoginPayload {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String mobile;

}