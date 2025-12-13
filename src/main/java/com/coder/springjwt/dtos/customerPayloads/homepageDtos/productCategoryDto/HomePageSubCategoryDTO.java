package com.coder.springjwt.dtos.customerPayloads.homepageDtos.productCategoryDto;

import lombok.Data;

import java.util.List;

@Data
public class HomePageSubCategoryDTO {

    private String subCategoryName;
    private List<HomePageTypeCategoryDTO> types;


}
