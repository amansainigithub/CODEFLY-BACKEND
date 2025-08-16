package com.coder.springjwt.dtos.sellerPayloads.sellerPayload;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SellerStorePayload {

    @NotBlank
    private String storeName;

    private String tags;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
