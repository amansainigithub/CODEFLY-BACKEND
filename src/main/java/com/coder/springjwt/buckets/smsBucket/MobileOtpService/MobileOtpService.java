package com.coder.springjwt.buckets.smsBucket.MobileOtpService;


import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface MobileOtpService {

    void sendSMS(String number , String messageContent , String userRole , String areaMode) throws IOException;
}
