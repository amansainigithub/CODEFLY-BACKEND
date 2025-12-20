package com.coder.springjwt.dtos.adminDtos.dataOverviewDtos.useroverviewDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDataOverviewDto {

    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private Long id;
    private String username;
    private String email;
    private String mobile;
    private String sellerMobile;
    private String sellerMobileVerify;
    private String sellerEmailVerify;
    private String projectRole;
    private String sellerRegisterComplete;
    private String sellerEmail;
    private String sellerStoreName;

}
