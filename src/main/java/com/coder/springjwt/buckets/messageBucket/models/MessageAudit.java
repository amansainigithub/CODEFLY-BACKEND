package com.coder.springjwt.buckets.messageBucket.models;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message_audit")
public class MessageAudit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String toNumber;
    private String messageBody;
    private String status;
    private String twilioSid;
    private String errorMessage;
}
