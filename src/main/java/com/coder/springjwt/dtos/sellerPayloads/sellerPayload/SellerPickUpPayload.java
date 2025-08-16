package com.coder.springjwt.dtos.sellerPayloads.sellerPayload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellerPickUpPayload {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String addressLineOne;

    private String addressLineTwo;

    private String landmark;

    private String pincode;

    private String city;

    private String state;



}
