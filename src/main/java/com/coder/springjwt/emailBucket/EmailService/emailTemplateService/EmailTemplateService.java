package com.coder.springjwt.emailBucket.EmailService.emailTemplateService;

import com.coder.springjwt.emailBucket.emailPayloads.EmailTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface EmailTemplateService {
    ResponseEntity<?> createTemplate(EmailTemplate emailTemplate);
}
