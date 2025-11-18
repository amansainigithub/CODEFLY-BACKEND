package com.coder.springjwt.dtos.customerPayloads.productDetailsCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsCustomerDto {

    private Long productId;

    private String productName;

    private String defaultName;

    private String manufacturerName;

    private String brand;

    private String productMainImage;

    private String productPrice;

    private String productMrp;

    private String productDiscount;

    private List<ProductFilesDtos> ProductFilesDtos;

    private List<ProductSizesDto> productSizesDtos;
}
