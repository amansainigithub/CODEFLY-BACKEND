package com.coder.springjwt.dtos.adminDtos.productFilesManagerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilesManagerDto {

    private long id;

    private long productId;

    private String fileName;

    private String fileUrl;

    private String fileType;




}
