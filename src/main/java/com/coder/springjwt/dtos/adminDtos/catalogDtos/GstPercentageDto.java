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
public class GstPercentageDto {

    private long id;

    @Size(min=1, max=10)
    @NotBlank(message = "Gst Percentage must Not be Blank")
    private String gstPercentage;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
