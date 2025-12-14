package com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Data
@Setter
@Getter
public class CreateOrderDtoShipRocket {

    public String order_id;
    public String order_date;
    public String pickup_location;
    public String comment;
    public String billing_customer_name;
    public String billing_last_name;
    public String billing_address;
    public String billing_address_2;
    public String billing_city;
    public int billing_pincode;
    public String billing_state;
    public String billing_country;
    public String billing_email;
    public long billing_phone;
    public boolean shipping_is_billing;
    public String shipping_customer_name;
    public String shipping_last_name;
    public String shipping_address;
    public String shipping_address_2;
    public String shipping_city;
    public String shipping_pincode;
    public String shipping_country;
    public String shipping_state;
    public String shipping_email;
    public String shipping_phone;
    public String payment_method;
    public int shipping_charges;
    public int giftwrap_charges;
    public int transaction_charges;
    public int total_discount;
    public int sub_total;
    public int length;
    public int breadth;
    public int height;
    public double weight;

    public ArrayList<OrderItemDtoShipRocket> order_items;
}