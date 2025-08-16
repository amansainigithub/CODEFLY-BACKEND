package com.coder.springjwt.dtos.adminDtos.catalogDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductWeightDto {

    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "Catalog Weight must Not be Blank")
    private String productWeight;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
