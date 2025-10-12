package com.coder.springjwt.emailBucket.emailControllers.emailTemplateContriller;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.emailBucket.EmailService.emailTemplateService.EmailTemplateService;
import com.coder.springjwt.emailBucket.emailPayloads.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.EMAIL_TEMPLATE_CONTROLLER)
public class EmailTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @PostMapping(AdminUrlMappings.CREATE_TEMPLATE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTemplate(@Validated @RequestBody EmailTemplate emailTemplate) {
        return this.emailTemplateService.createTemplate(emailTemplate);
    }

}
