package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostModel;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NimbusPostApiLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String requestPayload;

    @Column(columnDefinition = "TEXT")
    private String responsePayload;

    @Column(length = 20)
    private String status;

    @Column(length = 10)
    private Integer httpStatus;
}
