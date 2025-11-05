package com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllStockDto {

    private long id;

    private String productName;

    private String productStatus;

    private String productKey;

    private String productDate;

    private String productTime;

    private String productMainFile;

    List<ProductInventoryDto> productInventories;

}
