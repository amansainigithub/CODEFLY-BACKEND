package com.coder.springjwt.emailBucket.EmailService.emailSenderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface EmailSenderService  {

    ResponseEntity<?> sendSimpleMail(String templateKey ,  Map<String,Object> data , Map<String,Object> emailSubjectData);
    ResponseEntity<?> sendHtmlMail(String templateKey , Map<String,Object> emailBodyData, Map<String,Object> emailSubjectData);
}
