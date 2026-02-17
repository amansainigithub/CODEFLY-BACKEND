package com.coder.springjwt.dtos.customerPayloads.cartItemDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItemsDto {

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    @JsonProperty("pPrice")
    private String pPrice;

    @JsonProperty("pBrand")
    private String pBrand;

    @JsonProperty("pSize")
    private String pSize;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("totalPrice")
    private Double totalPrice;

    @JsonProperty("pFileUrl")
    private String pFileUrl;

    @JsonProperty("pColor")
    private String pColor;

    @JsonProperty("pMrp")
    private Double pMrp;

    @JsonProperty("pCalculatedDiscount")
    private String pCalculatedDiscount;

    @JsonProperty("productRowId")
    private String productRowId;

}
