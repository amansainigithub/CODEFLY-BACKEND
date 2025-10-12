package com.coder.springjwt.emailBucket.EmailService.emailSenderService.imple;

import com.coder.springjwt.emailBucket.EmailService.emailSenderService.EmailSenderService;
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
@Service
@Slf4j
public class EmailSenderServiceImple implements EmailSenderService {


    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserHelper userHelper;


    public ResponseEntity<?> sendSimpleMail()
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
            mailMessage.setTo("amansaini1407@gmail.com");
            mailMessage.setSubject("SUBJECT HAIN YE");
            mailMessage.setText("BODY HAIN YE WITH-OUT HTML DATA");
            // Sending the mail
            javaMailSender.send(mailMessage);

            //Save Data to DB
//            this.saveEmailData(emailPayload);
            log.info("======Mail Sent Success========");

            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            //set mail Status
//            emailPayload.setStatus("FAILED");

            //Save Data to DB
//            this.saveEmailData(emailPayload);

            e.printStackTrace();
            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }

    }

//    public void saveEmailData(EmailPayload emailPayload)
//    {
//        Map<String,String> node =  userHelper.getCurrentUser();
//        node.get("username");
//        node.get("roles");
//
//        EmailBucket emailBucket = new EmailBucket();
//        emailBucket.setUser(node.get("username"));
//        emailBucket.setRole(emailPayload.getRole());
//        emailBucket.setContent(emailPayload.getContent());
//        emailBucket.setAreaMode(emailPayload.getAreaMode());
//        emailBucket.setStatus(emailPayload.getStatus());
//        emailBucket.setMailFrom(sender);
//        emailBucket.setMailTo(emailPayload.getRecipient());
//
//        this.emailRepository.save(emailBucket);
//        log.info("Data Saved Success Email Content ");
//    }

    @Override
    public ResponseEntity<?> sendHtmlMail() {

        log.info("HTML Mail Process Starting");

        MessageResponse response = new MessageResponse();

        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            // Setting up necessary details
            helper.setText("SEND HTML DATA", true);
            helper.setTo("amansaini1407@gmail.com");
            helper.setSubject("html subject");
            helper.setFrom(sender);

            // Sending the mail
            javaMailSender.send(mimeMessage);

            //set mail Status
//            emailHtmlPayload.setStatus("SUCCESS");

            //Save Data to DB
//            this.saveEmailHtmlData(emailHtmlPayload);
            log.info("======HTML Mail Sent Success========");

            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            //set mail Status
//            emailHtmlPayload.setStatus("FAILED");
//            //Save Data to DB
//            this.saveEmailHtmlData(emailHtmlPayload);

            e.printStackTrace();

            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }
    }


//    public void saveEmailHtmlData(EmailHtmlPayload emailHtmlPayload)
//    {
//        String username = "NEW_USER";
//        try {
//            Map<String,String> node =  userHelper.getCurrentUser();
//            username = node.get("username");
//        }
//        catch (Exception e)
//        {
//            e.getMessage();
//            log.error("User Registration New....");
//        }
//
//        EmailBucket emailBucket = new EmailBucket();
//        emailBucket.setUser(username);
//        emailBucket.setRole(emailHtmlPayload.getRole());
//        emailBucket.setContent(emailHtmlPayload.getHtmlContent());
//        emailBucket.setAreaMode(emailHtmlPayload.getAreaMode());
//        emailBucket.setStatus(emailHtmlPayload.getStatus());
//        emailBucket.setIsHtmlContent(Boolean.TRUE);
//        emailBucket.setMailFrom(sender);
//        emailBucket.setMailTo(emailHtmlPayload.getRecipient());
//
//        this.emailRepository.save(emailBucket);
//        log.info("Data Saved Success Email Content ");
//    }

}
