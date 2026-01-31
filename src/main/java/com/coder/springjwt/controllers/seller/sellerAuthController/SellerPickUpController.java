package com.coder.springjwt.controllers.seller.sellerAuthController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerPickUpPayload;
import com.coder.springjwt.services.sellerServices.sellerAuthenticationService.sellerPickupService.SellerPickUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_PICKUP_CONTROLLER)
public class SellerPickUpController {
    // In-memory store for sellers
    private static final Map<Long, Map<String, Object>> sellerStore = new HashMap<>();
    private static final Set<String> registeredMobiles = new HashSet<>();

    // STEP 1: SEND OTP
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> req) {
        String mobile = req.get("mobile");

        // Already registered check
        if (registeredMobiles.contains(mobile)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "Mobile number already registered"));
        }


        // Dummy sellerId
        Long sellerId = System.currentTimeMillis();

        // Create initial seller object in memory
        Map<String, Object> sellerData = new HashMap<>();
        sellerData.put("id", sellerId);
        sellerData.put("mobile", mobile);
        sellerData.put("otpVerified", false);
        sellerData.put("step", 1); // current step
        sellerStore.put(sellerId, sellerData);

        System.out.println("New Mobile Number | OTP sent Successfully---");

        return ResponseEntity.ok(Map.of(
                "success", true,
                "id", sellerId,
                "mobile", mobile,
                "message", "OTP sent successfully (123456)"
        ));
    }

    // STEP 2: VERIFY OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> req) {
        Long id = Long.valueOf(req.get("id"));
        String otp = req.get("otp");

        Map<String, Object> seller = sellerStore.get(id);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Seller not found"));
        }

        if ("123456".equals(otp)) {
            seller.put("otpVerified", true);
            seller.put("step", 3); // move to business details
            registeredMobiles.add((String) seller.get("mobile"));
            System.out.println("OTP Verified Successfully");
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "OTP verified successfully"
            ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("success", false, "message", "Invalid OTP"));
    }

    // STEP 3: SAVE BUSINESS DETAILS
    @PostMapping("/save-business/{id}")
    public ResponseEntity<?> saveBusiness(@PathVariable Long id,
                                          @RequestBody Map<String, Object> data) {

        Map<String, Object> seller = sellerStore.get(id);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Seller not found"));
        }

        // Merge business details
        seller.putAll(data);
        seller.put("step", 4); // next step → pickup address

        System.out.println("Business Details Saved Success");

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Business details saved",
                "sellerId", id,
                "data", seller
        ));
    }



    // STEP 4: SAVE PICKUP ADDRESS
    @PostMapping("/save-pickup/{id}")
    public ResponseEntity<?> savePickupAddress(@PathVariable Long id,
                                               @RequestBody Map<String, Object> data) {

        Map<String, Object> seller = sellerStore.get(id);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Seller not found"));
        }

        // Merge pickup address details
        seller.putAll(data);
        seller.put("step", 5); // next step → bank details

        System.out.println("Save Seller pickup Data");

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Pickup address saved successfully",
                "sellerId", id,
                "data", seller
        ));
    }



    // STEP 5: SAVE BANK DETAILS
    @PostMapping("/save-bank/{id}")
    public ResponseEntity<?> saveBankDetails(@PathVariable Long id,
                                             @RequestBody Map<String, Object> data) {

        Map<String, Object> seller = sellerStore.get(id);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Seller not found"));
        }

        // Merge bank details
        seller.putAll(data);
        seller.put("step", 6); // next step → category selection or final submit

        System.out.println("Save Bank Details...");

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Bank details saved successfully",
                "sellerId", id,
                "data", seller
        ));
    }



    // STEP 6: Save Store Name + Final Submit
    @PostMapping("/save-store-name/{id}")
    public ResponseEntity<?> saveStoreName(@PathVariable Long id,
                                           @RequestBody Map<String, Object> data) {

        Map<String, Object> seller = sellerStore.get(id);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Seller not found"));
        }

        // Save store name
        seller.put("storeName", data.get("storeName"));
        seller.put("step", 7); // registration complete
        seller.put("status", "REVIEW_PENDING"); // mark for review

        System.out.println("Save Seller Store Name...");

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Seller registration submitted for review",
                "sellerId", id,
                "data", seller
        ));
    }





}




