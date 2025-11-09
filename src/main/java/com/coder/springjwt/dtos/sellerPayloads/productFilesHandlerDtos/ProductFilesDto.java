package com.coder.springjwt.dtos.sellerPayloads.productFilesHandlerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilesDto {

    private long id;

    private long productId;

    private String fileName;

    private String fileUrl;

    private String fileType;
}
