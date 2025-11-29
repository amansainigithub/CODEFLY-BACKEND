package com.coder.springjwt.buckets.razorpayBucket.razorpayController;

import com.coder.springjwt.buckets.razorpayBucket.razorpayDtos.RazorpaySignatureDto;
import com.coder.springjwt.buckets.razorpayBucket.razorpayServices.RazorpayService;
import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.RAZORPAY_CONTROLLER)
public class RazorpayController {

    @Autowired
    private RazorpayService  razorpayService;


    @PostMapping(AdminUrlMappings.CREATE_RAZORPAY_ORDER)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRazorpayOrder( @PathVariable double amount ) {
        Order razorpayOrder = razorpayService.createRazorpayOrder(amount);
        return ResponseEntity.ok(razorpayOrder.toString());
    }

    @GetMapping(AdminUrlMappings.FETCH_RAZORPAY_ORDER_DETAILS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> fetchRazorpayOrderDetails(@PathVariable String orderId) {
        return ResponseEntity.ok(razorpayService.fetchRazorpayOrderDetails(orderId));
    }


    @GetMapping(AdminUrlMappings.FETCH_RAZORPAY_PAYMENT_DETAILS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> fetchRazorpayPaymentDetails(@PathVariable String paymentId) {
        return ResponseEntity.ok(razorpayService.fetchRazorpayPaymentDetails(paymentId));
    }


    @PostMapping(AdminUrlMappings.VERIFY_RAZORPAY_SIGNATURE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> verifyRazorpaySignature(@RequestBody RazorpaySignatureDto razorpaySignatureDto) {
        return ResponseEntity.ok(razorpayService.verifyRazorpaySignature(razorpaySignatureDto));
    }

}
