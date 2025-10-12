package com.coder.springjwt.emailBucket.EmailService.emailSenderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface EmailSenderService  {

    ResponseEntity<?> sendSimpleMail();
    ResponseEntity<?> sendHtmlMail();
}
