package com.coder.springjwt.emailBucket.emailControllers.emailSenderServiceController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.emailBucket.EmailService.emailSenderService.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AdminUrlMappings.ADMIN_PUBLIC_URL +"/EmailSenderServiceController")
public class EmailSenderServiceController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("sendSimpleEmail")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendSimpleEmail() {

        Map<String,Object> emailSubjectData = new HashMap<>();
        emailSubjectData.put("productName", "Dell Laptop i5 Generation 16GB RAM 1 GB SSD");

        Map<String,Object> emailBody = new HashMap<>();
        emailBody.put("productName", "Wireless Mouse");
        emailBody.put("sellerName", "Aman Suryavanshi");

        return this.emailSenderService.sendSimpleMail("PRODUCT_APPROVED", emailSubjectData ,emailBody);
    }


    @PostMapping("sendHtmlEmail")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendHtmlEmail() {

        Map<String,Object> emailSubjectData = new HashMap<>();
        emailSubjectData.put("productName", "Dell Laptop i5 Generation 16GB RAM 512 GB SSD");

        Map<String,Object> emailBodyData = new HashMap<>();
        emailBodyData.put("productName", "Wireless Mouse");
        emailBodyData.put("sellerName", "Aman Suryavanshi");
        emailBodyData.put("product_id", "10020030015487800");


        return this.emailSenderService.sendHtmlMail("PRODUCT_APPROVED", emailBodyData , emailSubjectData);
    }



}
