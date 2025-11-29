package com.coder.springjwt.buckets.razorpayBucket.razorpayServices.imple;

import com.coder.springjwt.buckets.razorpayBucket.razorpayDtos.RazorpayOrderDetailsDto;
import com.coder.springjwt.buckets.razorpayBucket.razorpayDtos.RazorpaySignatureDto;
import com.coder.springjwt.buckets.razorpayBucket.razorpayServices.RazorpayService;
import com.coder.springjwt.util.ResponseGenerator;
import com.razorpay.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class RazorpayServiceImple implements RazorpayService {

    private static final String RAZORPAY_KEY = "rzp_test_cFBctGmM8MII0E";

    private static final String RAZORPAY_SECRET = "VgYZ2olzRXQfv87xzvakT9Va";



    //Create Razorpay Order
    public Order createRazorpayOrder(Double amount) {
        try {
            RazorpayClient client = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

            // Prepare Order Request
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", "INR");

            // PRINT REQUEST DATA
            log.info("Razorpay Order Request: " + orderRequest.toString());

            // Create Order
            Order order = client.Orders.create(orderRequest);

            // PRINT FULL ORDER RESPONSE
            log.info("Razorpay Order Response (complete JSON): " + order.toString());
            log.info("Order ID: " + order.get("id"));
            log.info("Amount: " + order.get("amount"));
            log.info("Currency: " + order.get("currency"));
            log.info("Status: " + order.get("status"));
            log.info("Created At: " + order.get("created_at"));

            return order;

        } catch (RazorpayException e) {
            log.error("Razorpay error: ", e);
            throw new RuntimeException("Razorpay error: " + e.getMessage());

        } catch (Exception e) {
            log.error("Error creating Razorpay order", e);
            throw new RuntimeException("Server error while creating Razorpay order");
        }

    }

    @Override
    public ResponseEntity<?> fetchRazorpayOrderDetails(String orderId) {
        try {
            RazorpayClient client = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

            // Fetch order details
            Order order = client.Orders.fetch(orderId);

            log.info(order.toString());

            RazorpayOrderDetailsDto dto = new RazorpayOrderDetailsDto();
            dto.setOrderId(order.get("id"));
            dto.setAmount(order.get("amount"));
            dto.setAmountPaid(order.get("amount_paid"));
            dto.setAmountDue(order.get("amount_due"));
            dto.setCurrency(order.get("currency"));
            dto.setStatus(order.get("status"));
            dto.setAttempts(order.get("attempts"));
            Date date = order.get("created_at");
            dto.setCreatedAt(String.valueOf(date.getTime()));  // convert to timestamp

            log.info("Mapped DTO: " + dto.toString());

            return ResponseGenerator.generateSuccessResponse(dto, "SUCCESS");
        } catch (RazorpayException e) {
            log.error("Razorpay fetch error:", e);
            return ResponseGenerator.generateBadRequestResponse("Error fetching Razorpay order: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> fetchRazorpayPaymentDetails(String paymentId) {
        try {
            RazorpayClient client = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

            // Fetch Razorpay payment
            Payment payment = client.Payments.fetch(paymentId);
            System.out.println("PAYMENTS :: " + payment.toString());

            return ResponseGenerator.generateSuccessResponse(payment.toString(), "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse("Unable to fetch payment details");
        }
    }


    @Override
    public ResponseEntity<?> verifyRazorpaySignature(RazorpaySignatureDto razorpaySignatureDto) {
        try {
            String orderId = razorpaySignatureDto.getRazorpay_order_id();
            String paymentId = razorpaySignatureDto.getRazorpay_payment_id();
            String signature = razorpaySignatureDto.getRazorpay_signature();

            String key = orderId + "|" + paymentId;

            boolean signatureResult  = Utils.verifySignature(key, signature, RAZORPAY_SECRET);
            return ResponseGenerator.generateSuccessResponse(signatureResult , "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(false);
        }
    }


}
