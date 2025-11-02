package com.coder.springjwt.buckets.emailBucket.EmailService.emailTemplateService;

import com.coder.springjwt.buckets.emailBucket.emailBucketDtos.EmailTemplateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface EmailTemplateService {
    ResponseEntity<?> createEmailTemplate(EmailTemplateDto emailTemplateDto);

    ResponseEntity<?> deleteEmailTemplate(long templateId);

    ResponseEntity<?> getEmailTemplate(long templateId);

    ResponseEntity<?> updateEmailTemplate(EmailTemplateDto emailTemplateDto);
    ResponseEntity<?> getEmailTemplateData(Integer page, Integer size);



}
