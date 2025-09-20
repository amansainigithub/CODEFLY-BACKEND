package com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeRowsDto {
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
}
