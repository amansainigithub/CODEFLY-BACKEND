package com.coder.springjwt.buckets.cashFreeBucket;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(SellerUrlMappings.CASH_FREE_CONTROLLER)
public class CashFreeController {


    @PostMapping(SellerUrlMappings.CREATE_CASHFREE_ORDER)
//    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> createCashFreeOrder(@RequestBody OrderRequest orderRequest) {

        System.out.println("orderRequest :" + orderRequest.toString());

        Object order = this.createOrder(2.00, "amansaini1407@gmail.com", "9818644140");
        System.out.println(order.toString());
        return ResponseEntity.ok(order);
    }




    private String clientId = "TEST10273801f926777210256c1f133b10837201";
    private String clientSecret = "cfsk_ma_test_6d8f486cc954082eac55bea32889fb97_be734858";
    private String environment = "sandbox";

    public Object createOrder(double amount, String email, String phone) {

        System.out.println("CashFree Works");
        String url = environment.equals("sandbox") ?
                "https://sandbox.cashfree.com/pg/orders" :
                "https://api.cashfree.com/pg/orders";

        JSONObject body = new JSONObject();
        String orderId = "ORD_" + System.currentTimeMillis();

        body.put("order_id", orderId);
        body.put("order_amount", amount);
        body.put("order_currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("customer_id", "CUST_" + System.currentTimeMillis());
        customer.put("customer_email", email);
        customer.put("customer_phone", phone);
        body.put("customer_details", customer);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-client-id", clientId);
        headers.set("x-client-secret", clientSecret);
        headers.set("x-api-version", "2023-08-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, entity, Object.class);
    }
}
