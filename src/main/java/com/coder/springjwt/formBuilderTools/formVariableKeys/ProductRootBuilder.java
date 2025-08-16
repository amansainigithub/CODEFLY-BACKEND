package com.coder.springjwt.formBuilderTools.formVariableKeys;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductRootBuilder {

    private long  id;

    private String  productName;
    private String  gst;
    private String  hsn;
    private String productWeight;


    private List<String> productSizes;

    //Product table Data
    private List<ProductRows> tableRows;

    //Product Details
    private String sleeveType;
    private String fitType;
    private String gender;
    private String materialType;
    private String productColor;
    private String country;
    private String pattern;
    private String manufactureName;
    private String netQuantity;
    private String productId;
    private String productCode;


    //Product Other Details
    private String finishingType;
    private String brandField;
    private String description;

    private String variant;

    //Variant Data
    private List<FormProductVariantBuilder> productVariants;


}
