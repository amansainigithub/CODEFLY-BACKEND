package com.coder.springjwt.dtos.customerPayloads.homepageDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDataDto {

    private Long productId;

    private String productName;

    private String defaultName;

    private String manufacturerName;

    private String brand;

    private String productDate;

    private String productTime;

    private String productStatus;

    private String pattern;

    private String price;
    private String mrp;
    private String productDiscount;

    private String inventory;


    private String productMainFileUrl;

    private String fileName;


}
