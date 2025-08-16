package com.coder.springjwt.services.emailServices.EmailService;

import com.coder.springjwt.dtos.emailPayloads.EmailHtmlPayload;
import com.coder.springjwt.dtos.emailPayloads.EmailPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {

    public ResponseEntity<?> sendSimpleMail(EmailPayload emailPayload);

    public  ResponseEntity<?> sendHtmlMail(EmailHtmlPayload emailHtmlPayload);

    public void saveEmailData(EmailPayload emailPayload);

}
