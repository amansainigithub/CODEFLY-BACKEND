package com.coder.springjwt.models.sellerModels.productModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "ProductDetails")
public class ProductDetailsModel extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private String defaultName;
    private String gst;
    private String hsnCode;
    private String netWeight;
    private List<String> productSizes;
    private String color;
    private String netQuantity;
    private String neck;
    private String occasion;
    private String pattern;
    private String sleeveLength;
    private String countryOfOrigin;
    private String manufacturerName;
    private String manufacturerAddress;
    private String manufacturerPincode;
    private String brand;
    private String lining;
    private String closureType;
    private String stretchType;
    private String careInstruction;
    private String description;
    private String cottonType;

    //Added Columns Extra
    private long variantId;
    private String variantName;
    private String productSeries;
    private String userId;
    private String username;

    //SAVE DATE AND TIME WITH FORMAT
    private String productDate;
    private String productTime;

    //Product Status
    private String productStatus;

    //Product Root ID
    private long productRootId;
    //Product key
    private String productKey;

    //Product UID
    private String productUid;


    //Service TAX GST , TDS , TCS , SHIPPING CHARGES , FINAL PRODUCT PRICE
    private String productGst;
    private String productTcs;
    private String productTds;
    private String productPrice;
    private String productMrp;
    private String bankSettlementAmount;
    private String bankSettlementWithShipping;

    //Shipping Charges
    private String shippingCharges;
    private String shippingFee;
    private String shippingTotal;

    //Product Discount
    private String productDiscount;

    //Product Approved and Dis-Approved DateTime and ProductDis-Approved
    private String productApprovedDate;
    private String productApprovedTime;
    private String productDisApprovedDate;
    private String productDisApprovedTime;

    //Approved By
    private String approvedBy;

    //Product Approved Reason only Current Reason Stored--
    private String productApprovedReason;

    //Product Dis Approved Description
    private String productDisApprovedDesc;


    @OneToMany(cascade = CascadeType.ALL ,mappedBy ="productDetailsModel" , orphanRemoval = true )
    @JsonIgnore
    private List<ProductFiles> productFiles;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy ="productDetailsModel" , orphanRemoval = true )
//    @JsonIgnore
    private List<ProductSizeRows> productSizeRows;


    @ManyToOne
    @JoinColumn( referencedColumnName = "id" , name = "product_root_joiner")
    @JsonIgnore
    private ProductRoot productRoot;
}
