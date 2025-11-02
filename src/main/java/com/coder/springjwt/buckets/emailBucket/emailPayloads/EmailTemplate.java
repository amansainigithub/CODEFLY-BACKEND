package com.coder.springjwt.buckets.emailBucket.emailPayloads;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EmailTemplate")
public class EmailTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String templateKey;

    private String subject;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String bodyHtml;

    private String module;
}
