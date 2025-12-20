package com.coder.springjwt.dtos.adminDtos.dataOverviewDtos.useroverviewDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDataOverviewDto {

    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private Long id;
    private String username;
    private String email;
    private String mobile;
    private String customerMobileVerify;
    private String custUsername;
    private String customerMobileOtp;
    private String projectRole;
    private String customerRegisterComplete;

}
