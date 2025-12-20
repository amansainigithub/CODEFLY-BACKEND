package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostService;

import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusPostCancelShipmentRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusPostLoginRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusServiceabilityRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.shipment.NimbusPostShipmentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface NimbusPostService {
    ResponseEntity<?> generateTokenNimbusPost(NimbusPostLoginRequest nimbusPostLoginRequest);

    ResponseEntity<?> createShipmentNimbusPost(NimbusPostShipmentRequest nimbusPostShipmentRequest, String token);

    ResponseEntity<?> cancelShipmentNimbusPost(NimbusPostCancelShipmentRequest nimbusPostCancelShipmentRequest, String token);

    ResponseEntity<?> trackShipmentNimbusPost(String awb, String bearer_);

    ResponseEntity<?> serviceAbilityNimbusPost(NimbusServiceabilityRequest nimbusServiceabilityRequest, String token);
}
