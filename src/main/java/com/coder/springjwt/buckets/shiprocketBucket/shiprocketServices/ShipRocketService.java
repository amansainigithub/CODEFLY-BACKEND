package com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.GenerateTokenDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.PickUpLocationDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ShipRocketService {
//    ResponseEntity<?> generateTokenShipRocket(GenerateTokenDtoShipRocket generateTokenDtoShipRocket);

    ResponseEntity<?> addPickupLocationShipRocket(PickUpLocationDtoShipRocket pickUpLocationDtoShipRocket);

    ResponseEntity<?> createOrderShipRocket(CreateOrderDtoShipRocket createOrderDtoShipRocket);

    ResponseEntity<?> orderCancelShipRocket(CancelOrderDtoShipRocket cancelOrderDtoShipRocket);

    ResponseEntity<?> checkCourierAvailabilityShipRocket(CheckServiceAvailability checkServiceAvailability);

    ResponseEntity<?> assignAwbNumberShipRocket(GenerateAwbNumberShipRocket generateAwbNumberShipRocket);

    ResponseEntity<?> checkEstimateDeliveryTimeShipRocket1(CheckEstimateDeliveryTimeShipRocket checkEstimateDeliveryTimeShipRocket);


}
