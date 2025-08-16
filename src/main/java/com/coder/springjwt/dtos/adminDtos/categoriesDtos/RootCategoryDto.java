package com.coder.springjwt.dtos.adminDtos.categoriesDtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RootCategoryDto {


    private long id;

    @Column(unique = true,nullable = false )
    @Size(min=4, max=100)
    @NotBlank(message = "Category Name must Not be Blank")
    private String categoryName;

    private String defaultName;

    private String slug;

    private String description;

    @Column(length = 500)
    private String metaDescription;

    private String featuredStatus;

    private String categoryFile;

    private String permalink;

    private String user;

    private boolean isActive = Boolean.FALSE;
}
