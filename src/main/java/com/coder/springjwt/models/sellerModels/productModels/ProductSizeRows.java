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
    private long productRootId;
    private String productKey;
    private long productDetailsId;

    //Product UID
    private String productUid;

    //Service TAX GST , TDS , TCS , SHIPPING CHARGES , FINAL PRODUCT PRICE
    private String productGst;
    private String productTcs;
    private String productTds;
    private String bankSettlementAmount;
    private String bankSettlementWithShipping;

    //Shipping Charges
    private String shippingCharges;
    private String shippingFee;
    private String shippingTotal;

    //Product Discount
    private String productDiscount;

    @ManyToOne
    @JoinColumn( referencedColumnName = "id" , name = "product_details_joiner")
    @JsonIgnore
    private ProductDetailsModel productDetailsModel;
}
