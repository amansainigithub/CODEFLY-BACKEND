package com.coder.springjwt.buckets.emailBucket.emailBucketDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplateDto {

    private Long id;
    private String templateKey;
    private String subject;
    private String bodyHtml;
    private String module;
}
