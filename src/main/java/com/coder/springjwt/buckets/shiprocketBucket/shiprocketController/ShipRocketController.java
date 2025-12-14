package com.coder.springjwt.buckets.shiprocketBucket.shiprocketController;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.GenerateTokenDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.PickUpLocationDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.CancelOrderDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.CreateOrderDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices.ShipRocketService;
import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.SHIP_ROCKET_CONTROLLER)
public class ShipRocketController {

    @Autowired
    private ShipRocketService shipRocketService;

    @PostMapping(AdminUrlMappings.GENERATE_TOKEN_SHIP_ROCKET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> generateTokenShipRocket(@RequestBody GenerateTokenDtoShipRocket generateTokenDtoShipRocket) {
        return shipRocketService.generateTokenShipRocket(generateTokenDtoShipRocket);
    }

    @PostMapping(AdminUrlMappings.ADD_PICKUP_LOCATION_SHIP_ROCKET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addPickupLocationShipRocket(@RequestBody PickUpLocationDtoShipRocket pickUpLocationDtoShipRocket) {
        return shipRocketService.addPickupLocationShipRocket(pickUpLocationDtoShipRocket);
    }


    @PostMapping(AdminUrlMappings.CREATE_ORDER_SHIP_ROCKET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createOrderShipRocket(@RequestBody CreateOrderDtoShipRocket createOrderDtoShipRocket) {
        return shipRocketService.createOrderShipRocket(createOrderDtoShipRocket);
    }

    @PostMapping(AdminUrlMappings.ORDER_CANCEL_SHIP_ROCKET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> orderCancelShipRocket(@RequestBody CancelOrderDtoShipRocket cancelOrderDtoShipRocket) {
        return shipRocketService.orderCancelShipRocket(cancelOrderDtoShipRocket);
    }
}
