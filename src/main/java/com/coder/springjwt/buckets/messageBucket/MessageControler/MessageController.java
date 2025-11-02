package com.coder.springjwt.buckets.messageBucket.MessageControler;

import com.coder.springjwt.buckets.messageBucket.messageRequestDtos.MessageRequestDto;
import com.coder.springjwt.buckets.messageBucket.messageService.MessageService;
import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.ADMIN_PUBLIC_URL +"/messageController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("sendMessage")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageRequestDto messageRequestDto) {
        return this.messageService.sendMessage(messageRequestDto);
    }


}
