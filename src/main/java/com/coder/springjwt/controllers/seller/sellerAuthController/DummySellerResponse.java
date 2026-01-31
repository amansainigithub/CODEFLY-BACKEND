package com.coder.springjwt.controllers.seller.sellerAuthController;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DummySellerResponse {

    private Long id;
    private String mobile;
    private boolean otpVerified;
    private String status;
}
