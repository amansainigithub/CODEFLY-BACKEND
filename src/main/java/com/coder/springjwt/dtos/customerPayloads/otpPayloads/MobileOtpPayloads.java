package com.coder.springjwt.dtos.customerPayloads.otpPayloads;


import lombok.Data;

@Data
public class MobileOtpPayloads {

    private String email;

    private String username;

    private String mobileOtp;
}
