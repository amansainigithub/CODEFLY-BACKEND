package com.coder.springjwt.buckets.messageBucket.messageRequestDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {

    @NotBlank
    private String to;

    @NotBlank
    private String body;
}
