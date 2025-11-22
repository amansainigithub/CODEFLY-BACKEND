package com.coder.springjwt.services.customerServices.orderPaymentService.imple;

import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.dtos.customerPayloads.cartItemDtos.CartItemsDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class OrderPaymentServiceHelper {

    public static String generateCustomOrderID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); // Timestamp format
        String timestamp = sdf.format(new Date());
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(90000); // 5-digit random number
        return "ORD-" + timestamp + randomNum; // Example: ORD202503311230451234
    }

}
