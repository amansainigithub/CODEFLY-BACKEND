package com.coder.springjwt.constants;

import org.springframework.stereotype.Component;

@Component
public class OtpMessageContent {

    public static String getMessageContent(String OTP)
    {
        return "Your OTP for E-COMM login is " + OTP + " and is valid for 30 mins. " +
                "Please DO NOT share this OTP with anyone to keep your account safe ";
    }

    public static String forgotPasswordOtpContent(String OTP)
    {
        return "We’ve sent a one-time password "+OTP+" to your registered Mobile Number is valid for 30 mins." +
                "Please DO NOT share this OTP with anyone to keep your account safe ";
    }

    public static String sellerRegistrationContent(String OTP)
    {
        return "We’ve sent a one-time password "+OTP+" to your registered Mobile Number is valid for 2 mins." +
                "Please DO NOT share this OTP with anyone to keep your account safe ";
    }
}
