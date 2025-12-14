package com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CreateOrderRequestResponse_SR")
public class CreateOrderRequestResponse_SR extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 4000)
    private String requestPacket;

    @Column(length = 4000)
    private String responsePacket;

    private String status;
}
