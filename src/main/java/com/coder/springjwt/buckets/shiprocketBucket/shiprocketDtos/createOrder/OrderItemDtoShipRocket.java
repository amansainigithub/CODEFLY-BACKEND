package com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OrderItemDtoShipRocket {

    public String name;
    public String sku;
    public int units;
    public int selling_price;
    public String discount;
    public String tax;
    public int hsn;
}
