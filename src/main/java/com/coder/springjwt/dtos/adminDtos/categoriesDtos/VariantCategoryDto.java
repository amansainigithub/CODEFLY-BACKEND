package com.coder.springjwt.dtos.adminDtos.categoriesDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VariantCategoryDto {


    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "Category Name must Not be Blank")
    private String categoryName;

    private String defaultName;

    private String slug;

    private String description;

    private String metaDescription;

    private String featuredStatus;

    private String categoryFile;

    private String permalink;

    private String user;

    private boolean isActive = Boolean.FALSE;

    @NotBlank
    @NotNull(message = "type Category Id must be Not Blank")
    private String typeCategoryId;
}
