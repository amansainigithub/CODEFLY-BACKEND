package com.coder.springjwt.formBuilderTools.formVariableKeys;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FormProductVariantBuilder {

    private String colorVariant;  // Variant color (can be the same as productColor)
    private String productLabel;  // Label/Size of the product (e.g., "L")
    private String productPrice;  // Price of the product
    private String productMrp;  // MRP (Maximum Retail Price)
    private String productInventory;  // Inventory count
    private String productLength;  // Length of the product
    private String waistSize;  // Waist size of the product
    private String shoulderWidth;  // Shoulder width of the product
    private String chestBustSize;  // Chest/Bust size of the product
    private String skuId;  // SKU (Stock Keeping Unit) identifier


}
