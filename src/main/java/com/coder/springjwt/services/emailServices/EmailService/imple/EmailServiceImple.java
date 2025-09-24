package com.coder.springjwt.services.emailServices.EmailService.imple;

import com.coder.springjwt.dtos.emailPayloads.EmailBucket;
import com.coder.springjwt.dtos.emailPayloads.EmailHtmlPayload;
import com.coder.springjwt.dtos.emailPayloads.EmailPayload;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.repository.emailRepository.EmailRepository;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
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
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class EmailServiceImple implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private UserHelper userHelper;


    public ResponseEntity<?> sendSimpleMail(EmailPayload emailPayload)
    {
        log.info("Simple Mail Process Starting");
        MessageResponse response = new MessageResponse();

        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailPayload.getRecipient());
            mailMessage.setText(emailPayload.getContent());
            mailMessage.setSubject(emailPayload.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);

            //set mail Status
            emailPayload.setStatus("SUCCESS");

            //Save Data to DB
            this.saveEmailData(emailPayload);
            log.info("======Mail Sent Success========");

            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            //set mail Status
            emailPayload.setStatus("FAILED");

            //Save Data to DB
            this.saveEmailData(emailPayload);

            e.printStackTrace();
            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }

    }

    public void saveEmailData(EmailPayload emailPayload)
    {
        Map<String,String> node =  userHelper.getCurrentUser();
        node.get("username");
        node.get("roles");

        EmailBucket emailBucket = new EmailBucket();
        emailBucket.setUser(node.get("username"));
        emailBucket.setRole(emailPayload.getRole());
        emailBucket.setContent(emailPayload.getContent());
        emailBucket.setAreaMode(emailPayload.getAreaMode());
        emailBucket.setStatus(emailPayload.getStatus());
        emailBucket.setMailFrom(sender);
        emailBucket.setMailTo(emailPayload.getRecipient());

        this.emailRepository.save(emailBucket);
        log.info("Data Saved Success Email Content ");
    }

    @Override
    public ResponseEntity<?> sendHtmlMail(EmailHtmlPayload emailHtmlPayload) {

        log.info("HTML Mail Process Starting");

        MessageResponse response = new MessageResponse();

        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            // Setting up necessary details
            helper.setText(emailHtmlPayload.getHtmlContent(), true);
            helper.setTo(emailHtmlPayload.getRecipient());
            helper.setSubject(emailHtmlPayload.getSubject());
            helper.setFrom(sender);

            // Sending the mail
            javaMailSender.send(mimeMessage);

            //set mail Status
            emailHtmlPayload.setStatus("SUCCESS");

            //Save Data to DB
            this.saveEmailHtmlData(emailHtmlPayload);
            log.info("======HTML Mail Sent Success========");

            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            //set mail Status
            emailHtmlPayload.setStatus("FAILED");
            //Save Data to DB
            this.saveEmailHtmlData(emailHtmlPayload);

            e.printStackTrace();

            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }
    }


    public void saveEmailHtmlData(EmailHtmlPayload emailHtmlPayload)
    {
        Map<String,String> node =  userHelper.getCurrentUser();
        node.get("username");
        node.get("roles");

        EmailBucket emailBucket = new EmailBucket();
        emailBucket.setUser(node.get("username"));
        emailBucket.setRole(emailHtmlPayload.getRole());
        emailBucket.setContent(emailHtmlPayload.getHtmlContent());
        emailBucket.setAreaMode(emailHtmlPayload.getAreaMode());
        emailBucket.setStatus(emailHtmlPayload.getStatus());
        emailBucket.setIsHtmlContent(Boolean.TRUE);
        emailBucket.setMailFrom(sender);
        emailBucket.setMailTo(emailHtmlPayload.getRecipient());

        this.emailRepository.save(emailBucket);
        log.info("Data Saved Success Email Content ");
    }


}
