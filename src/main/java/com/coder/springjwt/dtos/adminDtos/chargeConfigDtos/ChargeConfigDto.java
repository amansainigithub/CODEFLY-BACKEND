package com.coder.springjwt.dtos.adminDtos.chargeConfigDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeConfigDto {

    private long id;

    private String variantId;

    private String variantName;

    private String tcsCharge;

    private String tdsCharge;

    private String shippingCharge;

    private String shippingChargeFee;

    private String description;

    private boolean isActive;

    private String creationDate;

    private String lastModifiedDate;

}
