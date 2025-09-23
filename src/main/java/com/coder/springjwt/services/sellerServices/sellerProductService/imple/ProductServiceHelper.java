package com.coder.springjwt.services.sellerServices.sellerProductService.imple;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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



}
