package com.coder.springjwt.emailBucket.EmailService.emailTemplateService.imple;

import com.coder.springjwt.emailBucket.EmailService.emailTemplateService.EmailTemplateService;
import com.coder.springjwt.emailBucket.emailPayloads.EmailTemplate;
import com.coder.springjwt.emailBucket.emailRepository.EmailTemplateRepo;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceImple implements EmailTemplateService {


    @Autowired
    private EmailTemplateRepo emailTemplateRepo;

    @Autowired
    private UserHelper userHelper;
    
    @Override
    public ResponseEntity<?> createTemplate(EmailTemplate emailTemplate) {
        MessageResponse response =new MessageResponse();
        try {
            this.emailTemplateRepo.save(emailTemplate);
            response.setMessage("Email Template Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response ,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
    }
}
