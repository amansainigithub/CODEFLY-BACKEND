package com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class CancelOrderDtoShipRocket  {

    private List<Long> ids;
}
