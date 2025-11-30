package com.coder.springjwt.dtos.customerPayloads.productDetailsCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDto {

    private Long productId;

    private String productName;

    private String brand;

    private String productMainImage;

    private String productPrice;

    private String productMrp;
}
