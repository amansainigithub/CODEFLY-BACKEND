package com.coder.springjwt.buckets.messageBucket.messageService;

import com.coder.springjwt.buckets.messageBucket.messageRequestDtos.MessageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface MessageService {

    ResponseEntity<?> sendMessage(MessageRequestDto messageRequestDto);

}
