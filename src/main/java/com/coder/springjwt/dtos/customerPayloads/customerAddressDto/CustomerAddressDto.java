package com.coder.springjwt.dtos.customerPayloads.customerAddressDto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerAddressDto {

    private long id;
    private String country;
    private String customerName;
    private String mobileNumber;
    private String area;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private boolean defaultAddress;
    private String city;
    private String state;
}
