package com.coder.springjwt.dtos.customerPayloads.productDetailsCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilesDtos {

    private String pFileName;

    private String pFileUrl;

    private String pFileType;


}
