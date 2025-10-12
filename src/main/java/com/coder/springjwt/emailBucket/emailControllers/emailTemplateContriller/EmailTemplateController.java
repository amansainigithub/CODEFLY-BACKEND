package com.coder.springjwt.emailBucket.emailControllers.emailTemplateContriller;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.emailBucket.EmailService.emailTemplateService.EmailTemplateService;
import com.coder.springjwt.emailBucket.emailBucketDtos.EmailTemplateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.EMAIL_TEMPLATE_CONTROLLER)
public class EmailTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @PostMapping(AdminUrlMappings.CREATE_EMAIL_TEMPLATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmailTemplate(@Validated @RequestBody EmailTemplateDto emailTemplateDto) {
        return this.emailTemplateService.createEmailTemplate(emailTemplateDto);
    }


    @GetMapping(AdminUrlMappings.DELETE_EMAIL_TEMPLATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEmailTemplate(@PathVariable long templateId ) {
        return this.emailTemplateService.deleteEmailTemplate(templateId);
    }


    @GetMapping(AdminUrlMappings.GET_EMAIL_TEMPLATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEmailTemplate(@PathVariable long templateId ) {
        return this.emailTemplateService.getEmailTemplate(templateId);
    }


    @PostMapping(AdminUrlMappings.UPDATE_EMAIL_TEMPLATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEmailTemplate(@Validated @RequestBody EmailTemplateDto emailTemplateDto ) {
        return this.emailTemplateService.updateEmailTemplate(emailTemplateDto);
    }


    @PostMapping(AdminUrlMappings.GET_EMAIL_TEMPLATE_DATA)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEmailTemplateData(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.emailTemplateService.getEmailTemplateData(page,size);
    }
}
