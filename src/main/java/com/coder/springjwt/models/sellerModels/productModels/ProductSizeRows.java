package com.coder.springjwt.models.sellerModels.productModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "ProductSizeRows")
public class ProductSizeRows extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String price;
    private String mrp;
    private String inventory;
    private String skuCode;
    private String chestSize;
    private String lengthSize;
    private String shoulderSize;
    private String __msId;
    private String __msVal;

    //New Columns
    private String userId;
    private String username;

    ////Binding Variable
    private long productId;
    private String productKey;
    private long productDetailsId;

    @ManyToOne
    @JoinColumn( referencedColumnName = "id" , name = "product_details_joiner")
    @JsonIgnore
    private ProductDetailsModel productDetailsModel;
}
