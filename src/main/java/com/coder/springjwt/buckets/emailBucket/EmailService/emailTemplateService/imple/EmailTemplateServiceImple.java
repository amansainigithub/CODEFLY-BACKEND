package com.coder.springjwt.buckets.emailBucket.EmailService.emailTemplateService.imple;

import com.coder.springjwt.buckets.emailBucket.EmailService.emailTemplateService.EmailTemplateService;
import com.coder.springjwt.buckets.emailBucket.emailBucketDtos.EmailTemplateDto;
import com.coder.springjwt.buckets.emailBucket.emailPayloads.EmailTemplate;
import com.coder.springjwt.buckets.emailBucket.emailRepository.EmailTemplateRepo;
import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailTemplateServiceImple implements EmailTemplateService {


    @Autowired
    private EmailTemplateRepo emailTemplateRepo;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public ResponseEntity<?> createEmailTemplate(EmailTemplateDto emailTemplateDto) {
        MessageResponse response =new MessageResponse();
        try {
            EmailTemplate emailTemplate=  modelMapper.map(emailTemplateDto , EmailTemplate.class);

            this.emailTemplateRepo.save(emailTemplate);
            response.setMessage("Email Template Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response ,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage("DUPLICATE ENTRY ERROR");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteEmailTemplate(long templateId) {
        try {
            EmailTemplate emailTemplate = this.emailTemplateRepo.findById(templateId).orElseThrow(
                    () -> new DataNotFoundException("Email Template Not Found"));

            this.emailTemplateRepo.deleteById(emailTemplate.getId());
            log.info("Delete Success => Email Template Id :: " + emailTemplate.getId() );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Email Template Could not Delete");
            return ResponseGenerator.generateBadRequestResponse
                    ("Email Template Could not Delete :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getEmailTemplate(long templateId) {
        try {
            EmailTemplate  emailTemplate= this.emailTemplateRepo.findById(templateId).orElseThrow(
                    () -> new DataNotFoundException("Data not Found ! Error"));

            EmailTemplateDto emailTemplateDto= modelMapper.map(emailTemplate, EmailTemplateDto.class);
            log.info("Email Template Retrieved Data Success");
            return ResponseGenerator.generateSuccessResponse(emailTemplateDto , AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To Retrieved Email Template Data");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateEmailTemplate(EmailTemplateDto emailTemplateDto) {
        try {
            log.info("Update Email template Starting....");
            this.emailTemplateRepo.findById(emailTemplateDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            EmailTemplate emailTemplate =  modelMapper.map(emailTemplateDto , EmailTemplate.class);
            this.emailTemplateRepo.save(emailTemplate);

            log.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.SUCCESS ,
                    AdminMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.FAILED ,AdminMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getEmailTemplateData(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<EmailTemplate> emailTemplates = this.emailTemplateRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(emailTemplates.isEmpty())
            {
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(emailTemplates, AdminMessageResponse.SUCCESS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.FAILED);
        }
    }
}
