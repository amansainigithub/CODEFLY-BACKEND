package com.coder.springjwt.dtos.emailPayloads;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailPayload {
    private String recipient;

    private String subject;

    @Column(length = 10000)
    private String content;

    private String areaMode;

    private String status;

    private String role;




}
