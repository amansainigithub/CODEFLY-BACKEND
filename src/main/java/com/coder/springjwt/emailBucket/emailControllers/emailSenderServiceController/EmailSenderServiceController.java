package com.coder.springjwt.emailBucket.emailControllers.emailSenderServiceController;

import com.coder.springjwt.emailBucket.EmailService.emailSenderService.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("EmailSenderServiceController")
public class EmailSenderServiceController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("sendSimpleEmail")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendSimpleEmail() {
        return this.emailSenderService.sendSimpleMail();
    }


    @PostMapping("sendHtmlEmail")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendHtmlEmail() {
        return this.emailSenderService.sendHtmlMail();
    }



}
