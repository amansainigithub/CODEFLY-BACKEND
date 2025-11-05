package com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductInventoryDto {

    private long id;

    private String inventory;

    private String username;

    private String productSize;
}
