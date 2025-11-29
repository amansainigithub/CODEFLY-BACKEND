package com.coder.springjwt.buckets.razorpayBucket.razorpayServices;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RazorpayService {

    private static final String RAZORPAY_KEY = "rzp_test_cFBctGmM8MII0E";

    private static final String RAZORPAY_SECRET = "VgYZ2olzRXQfv87xzvakT9Va";



    public  Order createRazorpayOrder(Double amount)
    {
        //Create Razorpay Order
        try {
            RazorpayClient client = new RazorpayClient("rzp_test_cFBctGmM8MII0E", "VgYZ2olzRXQfv87xzvakT9Va");
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", "INR");
            Order order = client.Orders.create(orderRequest);
            return order;
        }catch (RazorpayException e) {
            throw new RuntimeException("Razorpay error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error creating Razorpay order", e);
            throw new RuntimeException("Server error while creating Razorpay order");
        }

    }




}
