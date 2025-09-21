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

    //Added Columns Extra
    private long variantId;
    private String variantName;
    private String productSeries;
    private String userId;
    private String username;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy ="productDetailsModel" , orphanRemoval = true )
    private List<ProductFiles> productFiles;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy ="productDetailsModel" , orphanRemoval = true )
    private List<ProductSizeRows> productSizeRows;


    @ManyToOne
    @JoinColumn( referencedColumnName = "id" , name = "product_root_joiner")
    @JsonIgnore
    private ProductRoot productRoot;
}
