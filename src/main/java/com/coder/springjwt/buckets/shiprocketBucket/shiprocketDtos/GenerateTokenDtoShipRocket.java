package com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class GenerateTokenDtoShipRocket {


    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;

}
