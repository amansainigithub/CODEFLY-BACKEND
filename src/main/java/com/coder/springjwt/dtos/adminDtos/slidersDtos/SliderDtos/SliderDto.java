package com.coder.springjwt.dtos.adminDtos.slidersDtos.SliderDtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SliderDto {

    private long id;

    @Column(nullable = false )
    @Size(min=4, max=100)
    @NotBlank(message = "slider Category Name must Not be Blank")
    private String sliderCategory;

    @Column(length = 500)
    private String title;

    @Column(length = 500)
    private String subTitle;

    @Column(length = 1000)
    private String description;

    private String fileUrl;

    private String fileId;

    private String username;

    private String routingLink;

    private boolean isActive = Boolean.FALSE;


}
