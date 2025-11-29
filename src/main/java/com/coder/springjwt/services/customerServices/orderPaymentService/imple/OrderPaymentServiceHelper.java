package com.coder.springjwt.services.customerServices.orderPaymentService.imple;

import com.coder.springjwt.repository.customerRepository.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerRepository.paymentOrderRepo.PaymentOrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
@Slf4j
public class OrderPaymentServiceHelper {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;

    public static String generateOrderReferenceNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // Timestamp format
        String timestamp = sdf.format(new Date());
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(90000); // 5-digit random number
        return "REF-" + timestamp + randomNum; // Example: ORD202503311230451234
    }

    public static String generateOrderIdPerItem() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss"); // Updated format: ddMMyyyy
        String timestamp = sdf.format(new Date());

        Random random = new Random();
        int randomNum = 1000 + random.nextInt(90000); // 5-digit random number

        return "ORD-" + timestamp + randomNum; // Example: ORD-1105202415301512345
    }

}
