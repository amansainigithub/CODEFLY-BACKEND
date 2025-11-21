package com.coder.springjwt.dtos.customerPayloads.homepageDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliderDto {


    private String title;

    private String subTitle;

    private String description;

    private String fileUrl;

    private String routingLink;

}
