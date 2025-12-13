package com.coder.springjwt.dtos.customerPayloads.homepageDtos.productCategoryDto;

import lombok.Data;

import java.util.List;

@Data
public class HomePageTypeCategoryDTO {

    private String typeCategoryName;
    private List<HomePageVariantCategoryDTO> variants;
}
