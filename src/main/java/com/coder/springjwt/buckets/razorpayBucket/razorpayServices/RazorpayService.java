package com.coder.springjwt.buckets.razorpayBucket.razorpayServices;

import com.coder.springjwt.buckets.razorpayBucket.razorpayDtos.RazorpaySignatureDto;
import com.razorpay.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RazorpayService {

    public  Order createRazorpayOrder(Double amount);

    public ResponseEntity<?> fetchRazorpayOrderDetails(String orderId);

    public ResponseEntity<?> fetchRazorpayPaymentDetails(String paymentId);

    public ResponseEntity<?> verifyRazorpaySignature(RazorpaySignatureDto razorpaySignatureDto);


}
