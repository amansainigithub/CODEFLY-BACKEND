package com.coder.springjwt.services.sellerServices.sellerProductService.imple;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ProductServiceHelper {


    // 5-digit counter (00000 - 99999) to avoid collisions inside same millisecond
    private static final AtomicInteger COUNTER = new AtomicInteger(new SecureRandom().nextInt(100_000));

    // SecureRandom for cryptographic quality randomness
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a 30-digit numeric product key.
     * Format: [13-digit epoch millis][05-digit counter][12-digit random]
     * Total length = 30 numeric characters.
     */
    public String generateProductKey() {
        long millis = System.currentTimeMillis();
        int count = COUNTER.getAndUpdate(i -> (i + 1) % 100_000);
        // produce a 12-digit random number (0 .. 999_999_999_999)
        long rand12 = (Math.abs(RANDOM.nextLong()) % 1_000_000_000_000L);
        return String.format("%013d%05d%012d", millis, count, rand12);
    }




    // GST calculate karne ka method
    public static BigDecimal calculateGST(BigDecimal price, BigDecimal gstPercentage) {
        if (price == null || gstPercentage == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(gstPercentage)        // price * gstPercentage
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP); // /100 and round 2 decimals
    }

    // TCS CALCULATION
    public static BigDecimal calculateTCS(BigDecimal price, BigDecimal gstPercentage) {
        BigDecimal gstAmount = calculateGST(price, gstPercentage);
        BigDecimal totalPrice = price.add(gstAmount);
        BigDecimal tcsRate = BigDecimal.valueOf(1); // TCS rate 1%
        return totalPrice.multiply(tcsRate)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    // TDS CALCULATION
    public static BigDecimal calculateTDS(BigDecimal price) {
        BigDecimal tdsRate = BigDecimal.valueOf(1); // TDS rate 1%
        return price.multiply(tdsRate)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }


    public static BigDecimal bankSettlement(BigDecimal actualPrice,
                                            BigDecimal gstAmount,
                                            BigDecimal tcsAmount,
                                            BigDecimal tdsAmount) {
        // actualPrice - (gst + tcs + tds)
        BigDecimal settlement = actualPrice.subtract(
                gstAmount.add(tcsAmount).add(tdsAmount)
        );
        // Round to 2 decimal places
        return settlement.setScale(2, RoundingMode.HALF_UP);
    }


    // Calculate discount percentage (2 decimal digits)
    public static float calculateDiscountPercent(float productMrp, float productPrice) {
        if (productMrp <= 0) return 0f;

        float discount = productMrp - productPrice;
        float discountPercent = (discount / productMrp) * 100f;

        // Round to 2 decimal places
        discountPercent = Math.round(discountPercent * 100f) / 100f;
        return discountPercent;
    }




}
