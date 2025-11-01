package com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRejectionReasonDto {

    private long id;

    private String code;

    private String reason;

    private String description;

    private boolean isActive;
}
