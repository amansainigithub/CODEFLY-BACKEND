package com.coder.springjwt.dtos.adminDtos.productStageDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDraftStageDto {

    private long id;
    private String userId;
    private String productName;
    private String productStatus;
    private String productKey;
    private String productDate;
    private String productTime;
    private String variantId;
    private String productMainFile;
    private String productSeries;

}
