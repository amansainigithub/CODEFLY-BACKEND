package com.coder.springjwt.models.sellerModels.sellerBank;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class SellerBank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String username;

    @NotBlank
    private String bankName;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String accountNumber;

    @NotBlank
    private String  ifscCode;

}
