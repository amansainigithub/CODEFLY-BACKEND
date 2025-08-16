package com.coder.springjwt.dtos.adminDtos.productStatusDtos;

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
public class ProductReviewStatusDto {

    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "Product Status must Not be Blank")
    @NotNull
    private String productStatus;

    @NotBlank(message = "Product Status Value must Not be Blank")
    @NotNull
    private String productStatusValue;

    private String description;

    private String mailSubject;

    private String mailBody;

    private boolean isActive = Boolean.TRUE;

}
