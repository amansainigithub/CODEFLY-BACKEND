package com.coder.springjwt.buckets.messageBucket.messageService.imple;

import com.coder.springjwt.buckets.messageBucket.messageBucketRepo.MessageAuditRepo;
import com.coder.springjwt.buckets.messageBucket.messageRequestDtos.MessageRequestDto;
import com.coder.springjwt.buckets.messageBucket.messageService.MessageService;
import com.coder.springjwt.buckets.messageBucket.models.MessageAudit;
import com.coder.springjwt.util.ResponseGenerator;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImple implements MessageService {


    @Value("${twilio.accountSid}")
    private String ACCOUNT_SID;
    @Value("${twilio.authToken}")
    private String AUTH_TOKEN;
    @Value("${twilio.fromNumber}")
    private String FROM_NUMBER;

    @Autowired
    private MessageAuditRepo messageAuditRepo;

    @PostConstruct
    public void init() {
        log.info("Twilio Initialization Complete...");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public ResponseEntity<?> sendMessage(MessageRequestDto messageRequestDto) {

        MessageAudit audit = new MessageAudit();
        String to = messageRequestDto.getTo();
        String body = messageRequestDto.getBody();
        try {
            audit.setToNumber(to);
            audit.setMessageBody(body);

            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(FROM_NUMBER),
                    body ).create();

            audit.setStatus(message.getStatus().toString());
            audit.setTwilioSid(message.getSid());

            //Save Message Audit
            this.messageAuditRepo.save(audit);
            return ResponseGenerator.generateSuccessResponse("SUCCESS");

        } catch (Exception ex) {
            ex.printStackTrace();
            audit.setStatus("FAILED");
            audit.setErrorMessage(ex.getMessage());

            //Save Message Audit
            this.messageAuditRepo.save(audit);

            return ResponseGenerator.generateBadRequestResponse();
        }
    }
}
