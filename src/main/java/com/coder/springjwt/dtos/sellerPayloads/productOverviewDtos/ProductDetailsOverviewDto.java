package com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ProductDetailsOverviewDto {

    private long id;
    private String productName;
    private String userId;
    private String productStatus;
    private String productKey;
    private String productDate;
    private String productTime;
    private String productMainFile;
}
