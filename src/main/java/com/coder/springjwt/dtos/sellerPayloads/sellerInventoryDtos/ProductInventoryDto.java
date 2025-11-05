package com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductInventoryDto {

    private long id;

    private String size;

    private String inventory;

}
