package com.coder.springjwt.dtos.sellerPayloads.sellerPayload;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellerTaxPayload {

    @NotBlank
    private String gstNumber;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
