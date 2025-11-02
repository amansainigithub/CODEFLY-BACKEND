package com.coder.springjwt.buckets.emailBucket.emailPayloads;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EmailSendAudit")
@Builder
public class EmailSendAudit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private String toEmail;

    private String subject;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String body;

    private String templateKey;

    private String status;

    private String errorMessage;

    private String triggeredBy;

    private String actionType;
}
