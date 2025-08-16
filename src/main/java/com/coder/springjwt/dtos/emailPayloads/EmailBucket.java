package com.coder.springjwt.dtos.emailPayloads;


import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "emailBucket")
public class EmailBucket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user;

    private String role;

    private String areaMode;

    @Column(length = 10000)
    private String content;

    private Boolean isHtmlContent;

    private String status;

    private String mailFrom;

    private String mailTo;

}
