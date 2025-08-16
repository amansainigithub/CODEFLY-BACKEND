package com.coder.springjwt.formBuilderTools.formVariableKeys;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductRows {

    private long id;
    private String productLabel;
    private String productPrice;
    private String productMrp;
    private String productLength;
    private String skuId;


    private String productInventory;
    private String waistSize;
    private String shoulderWidth;
    private String chestBustSize;

    private String colorVariant;


}
