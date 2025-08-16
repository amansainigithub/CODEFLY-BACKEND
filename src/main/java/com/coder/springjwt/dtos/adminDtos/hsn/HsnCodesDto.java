package com.coder.springjwt.dtos.adminDtos.hsn;

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
public class HsnCodesDto {

    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "HSN Code must Not be Blank")
    private String hsn;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.FALSE;


}
