package com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PickUpLocationDtoShipRocket {

    public String pickup_location;
    public String name;
    public String email;
    public String phone;
    public String address;
    public String address_2;
    public String city;
    public String state;
    public String country;
    public String pin_code;
}
