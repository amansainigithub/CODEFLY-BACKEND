package com.coder.springjwt.emailBucket.EmailService.emailSenderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface EmailSenderService  {

    ResponseEntity<?> sendSimpleMail(String templateKey , Map<String,Object> emailSubjectData ,  Map<String,Object> emailBodyData ,String toEmail);
    ResponseEntity<?> sendHtmlMail(String templateKey ,Map<String,Object> emailSubjectData , Map<String,Object> emailBodyData ,String toEmail);
}
