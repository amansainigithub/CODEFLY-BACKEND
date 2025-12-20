package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostController;

import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusPostCancelShipmentRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusPostLoginRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusServiceabilityRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.shipment.NimbusPostShipmentRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostService.NimbusPostService;
import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.NIMBUS_POST_CONTROLLER)
public class NimbusPostController {

    @Autowired
    private NimbusPostService nimbusPostService;


    @PostMapping(AdminUrlMappings.GENERATE_TOKEN_NIMBUS_POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generateTokenNimbusPost(@RequestBody NimbusPostLoginRequest nimbusPostLoginRequest) {
        return nimbusPostService.generateTokenNimbusPost(nimbusPostLoginRequest);
    }


    @PostMapping(AdminUrlMappings.CREATE_SHIPMENT_NIMBUS_POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createShipmentNimbusPost(@RequestBody NimbusPostShipmentRequest nimbusPostShipmentRequest,
                                                      @RequestHeader("token") String token) {
        System.out.println("createShipmentNimbusPost...");
        return nimbusPostService.createShipmentNimbusPost(nimbusPostShipmentRequest , token);
    }

    @PostMapping(AdminUrlMappings.CANCEL_SHIPMENT_NIMBUS_POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cancelShipmentNimbusPost(@RequestBody NimbusPostCancelShipmentRequest nimbusPostCancelShipmentRequest,
                                                      @RequestHeader("token") String token) {
        return nimbusPostService.cancelShipmentNimbusPost(nimbusPostCancelShipmentRequest , token);
    }

    @GetMapping(AdminUrlMappings.TRACK_SHIPMENT_NIMBUS_POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> trackShipmentNimbusPost(@PathVariable String awb,
                                                     @RequestHeader("token") String token) {
        return nimbusPostService.trackShipmentNimbusPost(awb , token);
    }

    @GetMapping(AdminUrlMappings.SERVICE_ABILITY_NIMBUS_POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> serviceAbilityNimbusPost(@RequestBody NimbusServiceabilityRequest nimbusServiceabilityRequest,
                                                      @RequestHeader("token") String token) {
        return nimbusPostService.serviceAbilityNimbusPost(nimbusServiceabilityRequest , token);
    }
}
