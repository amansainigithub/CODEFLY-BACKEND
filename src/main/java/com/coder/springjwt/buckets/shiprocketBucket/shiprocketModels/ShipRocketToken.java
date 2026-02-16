package com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shiprocket_token")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ShipRocketToken extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String token;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime expiryTime;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
