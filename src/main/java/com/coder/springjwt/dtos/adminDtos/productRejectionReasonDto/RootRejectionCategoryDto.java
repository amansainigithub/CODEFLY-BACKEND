package com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto;

import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.ProductRejectionReason;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RootRejectionCategoryDto {

    private long id;

    @NotBlank(message = "Root Rejection Category" )
    private String rootRejectionCategory;

    private String description;

    private boolean isActive = Boolean.FALSE;

    private List<ProductRejectionReason> productRejectionReasons;
}
