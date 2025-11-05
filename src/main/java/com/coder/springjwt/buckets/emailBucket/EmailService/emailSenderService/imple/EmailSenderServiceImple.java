package com.coder.springjwt.buckets.emailBucket.EmailService.emailSenderService.imple;

import com.coder.springjwt.buckets.emailBucket.EmailService.emailSenderService.EmailSenderService;
import com.coder.springjwt.buckets.emailBucket.emailPayloads.EmailSendAudit;
import com.coder.springjwt.buckets.emailBucket.emailPayloads.EmailTemplate;
import com.coder.springjwt.buckets.emailBucket.emailRepository.EmailSendAuditRepo;
import com.coder.springjwt.buckets.emailBucket.emailRepository.EmailTemplateRepo;
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

    @Autowired
    private EmailSendAuditRepo emailSendAuditRepo;


    public ResponseEntity<?> sendSimpleMail(String templateKey
                                            ,Map<String,Object>  emailSubjectData
                                            ,Map<String,Object> emailBodyData
                                            ,String toEmail
                                            )
    {
        log.info("Simple Mail Process Starting");
        MessageResponse response = new MessageResponse();
        String buildEmailSubject = null;
        String buildEmailBody = null;
        try {
            EmailTemplate emailTemplate = emailTemplateRepo.findByTemplateKey(templateKey)
                    .orElseThrow(() -> new RuntimeException("Template not found"));


            //Building Email Subject Dynamically
            buildEmailSubject = this.buildEmailSubject(emailTemplate.getSubject(), emailSubjectData);

            //Building Email Body Dynamically
            buildEmailBody = this.buildEmailBody(emailTemplate.getBodyHtml(), emailBodyData);

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(toEmail);
            mailMessage.setSubject(buildEmailSubject);
            mailMessage.setText(buildEmailBody);

            // Sending the mail
            javaMailSender.send(mailMessage);
            log.info("======Mail Sent Success========");
            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);

            // Save audit success
            saveEmailSendAudit(toEmail, buildEmailSubject, buildEmailBody, "SUCCESS", null, templateKey);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);

            //Save audit fail
            saveEmailSendAudit(toEmail, buildEmailSubject, buildEmailBody, "FAILED", e.getMessage(), templateKey);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }

    }


    @Override
    public ResponseEntity<?> sendHtmlMail(String templateKey ,
                                          Map<String,Object>  emailSubjectData,
                                          Map<String,Object> emailBodyData,
                                          String toEmail
                                          ) {

        log.info("HTML Mail Process Starting");
        MessageResponse response = new MessageResponse();
        String buildEmailSubject = null;
        String buildEmailBody = null;

        try {
            EmailTemplate emailTemplate = emailTemplateRepo.findByTemplateKey(templateKey)
                    .orElseThrow(() -> new RuntimeException("Template not found"));

            //Building Email Subject Dynamically
            buildEmailSubject = this.buildEmailSubject(emailTemplate.getSubject(), emailSubjectData);
            //Building Email Body Dynamically
            buildEmailBody = this.buildEmailBody(emailTemplate.getBodyHtml(), emailBodyData);

            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(toEmail);
            helper.setSubject(buildEmailSubject);
            helper.setText(buildEmailBody, true);
            helper.setFrom(sender);

            // Mail Sending
            javaMailSender.send(mimeMessage);
            log.info("======HTML Mail Sent Success========");
            response.setMessage("MAIL SEND SUCCESS");
            response.setStatus(HttpStatus.OK);

            // Save audit success
            saveEmailSendAudit(toEmail, buildEmailSubject, buildEmailBody, "SUCCESS", null, templateKey);
            return ResponseGenerator.generateSuccessResponse(response,"Success");
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setMessage("MAIL SEND FAILED");
            response.setStatus(HttpStatus.BAD_REQUEST);

            // Save audit success
            saveEmailSendAudit(toEmail, buildEmailSubject, buildEmailBody, "FAILED", e.getMessage(), templateKey);
            return ResponseGenerator.generateBadRequestResponse(response,"Failed");
        }
    }

    public String buildEmailBody(String emailTemplateBody , Map<String, Object> variables) {
        String body = emailTemplateBody;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            body = body.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
        }
        return body;
    }

    public String buildEmailSubject(String emailTemplateSubject , Map<String, Object> variables) {
        String subject = emailTemplateSubject;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            subject = subject.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
        }
        return subject;
    }




    // ============================================================
    //  AUDIT SAVE METHOD
    // ============================================================
    public void saveEmailSendAudit(String toEmail, String subject, String body,
                                   String status, String errorMessage, String actionType) {
        try {
            Map<String, String> currentUser = userHelper.getCurrentUser();
            String username = currentUser.get("username");


            EmailSendAudit audit = EmailSendAudit.builder()
                    .sender(this.sender)
                    .toEmail(toEmail)
                    .subject(subject)
                    .body(body)
                    .status(status)
                    .errorMessage(errorMessage)
                    .triggeredBy(username)
                    .actionType(actionType)
                    .build();

            this.emailSendAuditRepo.save(audit);
            log.info("Email Send Audit saved for: {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to save EmailSendAudit: {}", e.getMessage());
        }
    }

}
