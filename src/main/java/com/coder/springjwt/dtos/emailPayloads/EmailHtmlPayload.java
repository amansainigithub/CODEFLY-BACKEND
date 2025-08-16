package com.coder.springjwt.dtos.emailPayloads;

import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmailHtmlPayload {

    private String recipient;

    private String subject;

    //for send to Html content
    @Column(length = 10000)
    private String htmlContent;

    private String areaMode;

    private String status;

    private String role;

}
