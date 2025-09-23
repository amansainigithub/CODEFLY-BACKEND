package com.coder.springjwt.models.sellerModels.productModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ProductRoot")
public class ProductRoot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long variantId;

    private String variantName;

    //New Columns
    private String userId;

    private String username;

    private String productKey;

    //Product Status
    private String productStatus;

    @OneToMany(cascade = CascadeType.ALL,mappedBy ="productRoot" , orphanRemoval = true )
    private List<ProductDetailsModel> productDetailsModels;


}
