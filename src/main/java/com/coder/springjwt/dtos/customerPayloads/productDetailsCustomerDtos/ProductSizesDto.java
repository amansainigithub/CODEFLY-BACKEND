package com.coder.springjwt.dtos.customerPayloads.productDetailsCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizesDto {

    private String productPrice;
    private String productMrp;
    private String productInventory;
    private String productSize;
}
