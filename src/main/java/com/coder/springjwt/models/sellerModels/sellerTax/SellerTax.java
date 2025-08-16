package com.coder.springjwt.models.sellerModels.sellerTax;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "sellerTax")
@ToString
public class SellerTax extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String seller_key;

    @NotBlank
    @Column(unique = true)
    private String sellerUsername;

    @NotBlank
    @Column(unique = true , length = 15)
    private String gstNumber;

    private String sellerId;

    private String isValidate;

    private String gstRequest;

    private String gstResponse;



}
