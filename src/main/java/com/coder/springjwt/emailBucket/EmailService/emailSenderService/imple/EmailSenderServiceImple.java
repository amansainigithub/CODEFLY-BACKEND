package com.coder.springjwt.emailBucket.EmailService.emailSenderService.imple;

import com.coder.springjwt.emailBucket.EmailService.emailSenderService.EmailSenderService;
import com.coder.springjwt.emailBucket.EmailService.emailTemplateService.EmailTemplateService;
import com.coder.springjwt.emailBucket.emailBucketDtos.EmailTemplateDto;
import com.coder.springjwt.emailBucket.emailPayloads.EmailTemplate;
import com.coder.springjwt.emailBucket.emailRepository.EmailTemplateRepo;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailSenderServiceImple implements EmailSenderService {


    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private EmailTemplateRepo emailTemplateRepo;


    public ResponseEntity<?> sendSimpleMail(String templateKey
                                            ,Map<String,Object> emailBodyData
                                            ,Map<String,Object>  emailSubjectData)
    {
        log.info("Simple Mail Process Starting");
        MessageResponse response = new MessageResponse();

        try {
            EmailTemplate emailTemplate = emailTemplateRepo.findByTemplateKey(templateKey)
                    .orElseThrow(() -> new RuntimeException("Template not found"));


            //Building Email Subject Dynamically
            String buildEmailSubject = this.buildEmailSubject(emailTemplate.getSubject(), emailSubjectData);

            //Building Email Body Dynamically
            String buildEmailBody = this.buildEmailBody(emailTemplate.getBodyHtml(), emailBodyData);

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo("amansaini1407@gmail.com");
            mailMessage.setSubject(buildEmailSubject);
            mailMessage.setText(buildEmailBody);

            // Sending the mail
            javaMailSender.send(mailMessage);

            log.info("======Mail Sent Success========");
            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }

    }


    @Override
    public ResponseEntity<?> sendHtmlMail(String templateKey , Map<String,Object> emailBodyData , Map<String,Object>  emailSubjectData) {

        log.info("HTML Mail Process Starting");
        MessageResponse response = new MessageResponse();

        try {
            EmailTemplate emailTemplate = emailTemplateRepo.findByTemplateKey(templateKey)
                    .orElseThrow(() -> new RuntimeException("Template not found"));

            //Building Email Subject Dynamically
            String buildEmailSubject = this.buildEmailSubject(emailTemplate.getSubject(), emailSubjectData);
            //Building Email Body Dynamically
            String buildEmailBody = this.buildEmailBody(emailTemplate.getBodyHtml(), emailBodyData);

            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo("amansaini1407@gmail.com");
            helper.setSubject(buildEmailSubject);
            helper.setText(buildEmailBody, true);
            helper.setFrom(sender);

            // Mail Sending
            javaMailSender.send(mimeMessage);
            log.info("======HTML Mail Sent Success========");

            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }
        catch (Exception e) {

            e.printStackTrace();

            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }
    }

    public String buildEmailBody(String emailTemplateBody , Map<String, Object> variables) {
        String body = emailTemplateBody;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            body = body.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return body;
    }

    public String buildEmailSubject(String emailTemplateSubject , Map<String, Object> variables) {
        String subject = emailTemplateSubject;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            subject = subject.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return subject;
    }

}
