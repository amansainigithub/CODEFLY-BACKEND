package com.coder.springjwt.services.sellerServices.sellerProductService.imple;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
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


//    =============================================================================


    // GST CALCULATION
    public static BigDecimal calculateGST(BigDecimal price, BigDecimal gstPercentage) {
        if (price == null || gstPercentage == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(gstPercentage)        // price * gstPercentage
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP); // /100 and round 2 decimals
    }

    // TCS CALCULATION
    public static BigDecimal calculateTCS(BigDecimal price, BigDecimal gstPercentage , String tcsCharge) {
        BigDecimal gstAmount = calculateGST(price, gstPercentage);
        BigDecimal totalPrice = price.add(gstAmount);
        BigDecimal tcsRate = BigDecimal.valueOf(Long.parseLong(tcsCharge)); // TCS rate 1%
        return totalPrice.multiply(tcsRate)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    // TDS CALCULATION
    public static BigDecimal calculateTDS(BigDecimal price , String tdsCharge) {
        BigDecimal tdsRate = BigDecimal.valueOf(Long.parseLong(tdsCharge)); // TDS rate 1%
        return price.multiply(tdsRate)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }


    public static BigDecimal bankSettlement(BigDecimal actualPrice,
                                            BigDecimal gstAmount,
                                            BigDecimal tcsAmount,
                                            BigDecimal tdsAmount) {
        // actualPrice - (gst + tcs + tds)
        BigDecimal settlement = actualPrice.subtract(gstAmount.add(tcsAmount).add(tdsAmount));
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




    //SAVE PRODUCT FILES
// ================= IMAGE HANDLING =================
    public ResponseEntity<?> checkImageValidation(MultipartFile[] files) {
        List<String> allowedImageExtensions = Arrays.asList("jpg", "jpeg", "png");
        long maxImageSize = 5 * 1024 * 1024; // 5 MB

        int index = 1;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                log.info("Slot " + index + " is empty");
                index++;
                continue;
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.contains(".")) {
                return ResponseEntity.badRequest().body("Invalid file name at slot " + index);
            }

            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!allowedImageExtensions.contains(ext)) {
                return ResponseEntity.badRequest().body("Invalid file type at slot " + index + " (" + fileName + "). Only JPG and PNG allowed.");
            }

            if (file.getSize() > maxImageSize) {
                return ResponseEntity.badRequest().body("File " + fileName + " at slot " + index + " exceeds the 1 MB size limit.");
            }

            // Save image
//            log.info("Image accepted at slot " + index + " :: " + fileName + " | Size: " + file.getSize());

            index++;
        }
        return null; // null means SUCCESS
    }



    //VIDEO VALIDATION
    public ResponseEntity<?> checkIsVideoValid(MultipartFile video) {
        if (video == null || video.isEmpty()) {
            log.info("No video uploaded.");
            return null; // optional: return ResponseEntity.badRequest().body("Video is required");
        }

        String videoName = video.getOriginalFilename();
        if (videoName == null || !videoName.contains(".")) {
            return ResponseEntity.badRequest().body("Invalid video file name");
        }

        String videoExt = videoName.substring(videoName.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedVideoExtensions = Arrays.asList("mp4");

        long minVideoSize = 1 * 1024 * 1024;   // 1 MB
        long maxVideoSize = 50 * 1024 * 1024;  // 10 MB

        if (!allowedVideoExtensions.contains(videoExt)) {
            return ResponseEntity.badRequest().body("Invalid video type (" + videoName + "). Only MP4 allowed.");
        }

        if (video.getSize() < minVideoSize) {
            return ResponseEntity.badRequest().body("Video " + videoName + " must be at least 1 MB in size.");
        }

        if (video.getSize() > maxVideoSize) {
            return ResponseEntity.badRequest().body("Video " + videoName + " exceeds the 50 MB size limit.");
        }

        // Save video
        log.info("Video accepted :: " + videoName + " | Size: " + video.getSize());

        return null; // null means SUCCESS
    }




}
