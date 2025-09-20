package com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDetailsDto {

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

    private List<ProductSizeRowsDto> productSizeRows;

}
