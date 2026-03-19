package com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices.imple;

import com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo.*;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.GenerateTokenDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.PickUpLocationDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.*;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.*;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices.ShipRocketService;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
public class ShipRocketServiceImple implements ShipRocketService {


    @Autowired
    private GenerateTokenShipRocketRepo generateTokenSRRepository;

    @Autowired
    private PickUpAddressShipRocketRepo pickUpAddressShipRocketRepo;

    @Autowired
    private CreateOrderShipRocketRepo createOrderShipRocketRepo;

    @Autowired
    private CancelOrderShipRocketRepo cancelOrderShipRocketRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ShipRocketTokenRepository shipRocketTokenRepository;

    private static final String SHIP_ROCKET_LOGIN_URL = "https://apiv2.shiprocket.in/v1/external/auth/login";
    private static final String NEW_PICKUP_LOCATION_URL = "https://apiv2.shiprocket.in/v1/external/settings/company/addpickup";
    private static final String CREATE_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/create/adhoc";
    private static final String CANCEL_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/cancel";
    private static final String CHECK_SERVICE_AVAILABILITY = "https://apiv2.shiprocket.in/v1/external/courier/serviceability/";
    private static final String GENERATE_AWB_NUMBER = "https://apiv2.shiprocket.in/v1/external/courier/assign/awb";
    private static final String PICK_UP = "https://apiv2.shiprocket.in/v1/external/courier/generate/pickup";
    private static final String GET_SPECIFIC_ORDER_DETAILS = "https://apiv2.shiprocket.in/v1/external/orders/show/";

    private static final String GENERATE_LABEL_URL = "https://apiv2.shiprocket.in/v1/external/courier/generate/label";

//    @Override
//    public ResponseEntity<?> generateTokenShipRocket(GenerateTokenDtoShipRocket generateTokenDtoShipRocket) {
//        Map<String, Object> messageResponse = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        GenerateToken_SR generateToken_request = new GenerateToken_SR();
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            HttpEntity<GenerateTokenDtoShipRocket> entity = new HttpEntity<>(generateTokenDtoShipRocket, headers);
//            String requestJson = mapper.writeValueAsString(entity);
//
//            //save Request Packet
//            generateToken_request.setRequestPacket(requestJson);
//            GenerateToken_SR saveTokenData = this.generateTokenSRRepository.save(generateToken_request);
//
//
//            ResponseEntity<String> response = restTemplate.exchange(SHIP_ROCKET_LOGIN_URL, HttpMethod.POST, entity, String.class);
//            log.info("Ship Rocket Raw Response :: " + response.getBody());
//
//
//            JsonNode json = mapper.readTree(response.getBody());
//
//            if (response.getStatusCode().is2xxSuccessful() && json.has("token")) {
//
//                messageResponse.put("token", json.get("token").asText());
//                messageResponse.put("raw", json);
//
//                //Update Response Packet
//                saveTokenData.setResponsePacket(String.valueOf(json));
//                saveTokenData.setStatus("SUCCESS");
//                this.generateTokenSRRepository.save(saveTokenData);
//
//                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");
//            } else {
//                messageResponse.put("error", json);
//
//                //Update Response Packet
//                saveTokenData.setResponsePacket(String.valueOf(json));
//                saveTokenData.setStatus("FAILED");
//                this.generateTokenSRRepository.save(saveTokenData);
//
//                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
//            }
//        } catch (Exception e) {
//            generateToken_request.setStatus("FAILED");
//            this.generateTokenSRRepository.save(generateToken_request);
//            e.printStackTrace();
//            return ResponseGenerator.generateBadRequestResponse();
//        }
//    }


    public synchronized String getValidToken() {

        ShipRocketToken tokenEntity = shipRocketTokenRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);


        // 1️⃣ Token DB me nahi hai
        if (tokenEntity == null || tokenEntity.getToken() == null) {
            return generateAndSave();
        }

        // 2️⃣ Expiry Check (5 min buffer)
        if (tokenEntity.getExpiryTime() == null ||
                tokenEntity.getExpiryTime()
                        .minusMinutes(5)
                        .isBefore(LocalDateTime.now())) {
            return generateAndSave();
        }

        // 3️⃣ Token valid hai
        return tokenEntity.getToken();
    }


    private synchronized String generateAndSave() {

        log.info("Generating new Shiprocket token");

        GenerateTokenDtoShipRocket dto = new GenerateTokenDtoShipRocket();
        dto.setEmail("amansaini1407+00@gmail.com");
        dto.setPassword("a3$Ahz@JjAoB6UQ8KVROJsfdLGu7MHuz");

        JsonNode response = loginShipRocket(dto);

        String newToken = response.path("token").asText();

        if (newToken == null || newToken.isEmpty()) {
            throw new RuntimeException("Token not received from SHIP-ROCKET");
        }

        // 🔥 Expiry Extract
        LocalDateTime expiryTime = ShipRocketServiceHelper.extractExpiry(newToken);

        ShipRocketToken entity = shipRocketTokenRepository.findAll()
                .stream()
                .findFirst()
                .orElse(new ShipRocketToken());

        entity.setToken(newToken);
        entity.setExpiryTime(expiryTime);

        shipRocketTokenRepository.save(entity);

        log.info("NEW TOKEN SAVED SUCCESSFULLY");

        return newToken;
    }

    public JsonNode loginShipRocket(GenerateTokenDtoShipRocket dto) {
        try {
            log.info("Calling ShipRocket login API...");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GenerateTokenDtoShipRocket> entity =
                    new HttpEntity<>(dto, headers);

            ResponseEntity<String> response = restTemplate.exchange(SHIP_ROCKET_LOGIN_URL,
                                                                        HttpMethod.POST,
                                                                        entity,
                                                                        String.class );

            log.info("ShipRocket login response status: {}", response.getStatusCode());

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("ShipRocket login failed with status: "
                        + response.getStatusCode());
            }

            if (response.getBody() == null) {
                throw new RuntimeException("ShipRocket login returned empty body");
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.getBody());

            if (!json.has("token") || json.get("token").asText().isEmpty()) {
                throw new RuntimeException("Token not found in ShipRocket response");
            }

            log.info("Ship-Rocket token received successfully");

            return json;

        } catch (HttpClientErrorException ex) {

            log.error("Ship-Rocket login client error: {}", ex.getResponseBodyAsString());
            throw new RuntimeException("Ship-Rocket login client error", ex);

        } catch (HttpServerErrorException ex) {

            log.error("Ship-Rocket login server error: {}", ex.getResponseBodyAsString());
            throw new RuntimeException("Ship-Rocket login server error", ex);

        } catch (Exception e) {

            log.error("Unexpected error during Ship-Rocket login", e);
            throw new RuntimeException("Ship-Rocket login error", e);
        }
    }





    @Override
    public ResponseEntity<?> createOrderShipRocket(CreateOrderDtoShipRocket createOrderDtoShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        CreateOrderRequestResponse_SR createOrderReqRes = new CreateOrderRequestResponse_SR();
        try {
            String validToken = this.getValidToken();
            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(validToken);

            HttpEntity<Object> httpEntity = new HttpEntity<>(createOrderDtoShipRocket, headers);
            String requestPacket = objectMapper.writeValueAsString(httpEntity);

            //save Data
            createOrderReqRes.setRequestPacket(requestPacket);
            CreateOrderRequestResponse_SR orderData = this.createOrderShipRocketRepo.save(createOrderReqRes);

            //CALLING API
            ResponseEntity<String> response = restTemplate.exchange(CREATE_ORDER_URL, HttpMethod.POST, httpEntity, String.class);
            log.info("Create Ship Rocket Order Response :: " + response.getBody());

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                //Update Data to DB
                orderData.setResponsePacket(String.valueOf(jsonNode));
                orderData.setStatus("SUCCESS");
                this.createOrderShipRocketRepo.save(orderData);

                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");
            } else {
                //MESSAGE-RESPONSE
                messageResponse.put("raw", jsonNode);
                //Update Data to DB
                orderData.setResponsePacket(String.valueOf(jsonNode));
                orderData.setStatus("FAILED");
                this.createOrderShipRocketRepo.save(orderData);

                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (HttpClientErrorException e) {
            //MESSAGE-RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

            createOrderReqRes.setStatus("FAILED");
            createOrderReqRes.setResponsePacket(e.getMessage());
            this.createOrderShipRocketRepo.save(createOrderReqRes);
            e.printStackTrace();

        } catch (HttpServerErrorException e) {
            //MESSAGE-RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

            createOrderReqRes.setStatus("FAILED");
            createOrderReqRes.setResponsePacket(e.getMessage());
            this.createOrderShipRocketRepo.save(createOrderReqRes);

        } catch (Exception e) {
            //MESSAGE-RESPONSE
            messageResponse.put("Error",e.getMessage());

            System.out.println("UNKNOWN ERROR");
            e.printStackTrace();

            createOrderReqRes.setStatus("FAILED");
            createOrderReqRes.setResponsePacket(e.getMessage());
            this.createOrderShipRocketRepo.save(createOrderReqRes);
        }
        return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
    }


    @Override
    public ResponseEntity<?> addPickupLocationShipRocket(PickUpLocationDtoShipRocket pickUpLocationDtoShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        PickUpAddress_SR pickUpAddress_sr = new PickUpAddress_SR();

        try {
            String validToken = this.getValidToken();
            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 🔥 HARD-CODED TOKEN
            headers.setBearerAuth(validToken);

            HttpEntity<PickUpLocationDtoShipRocket> plsrEntity = new HttpEntity<>(pickUpLocationDtoShipRocket, headers);
            String requestJson = mapper.writeValueAsString(plsrEntity);

            //save Packet
            pickUpAddress_sr.setRequestPacket(requestJson);
            PickUpAddress_SR savePickUp = this.pickUpAddressShipRocketRepo.save(pickUpAddress_sr);

            ResponseEntity<String> response = restTemplate.exchange(NEW_PICKUP_LOCATION_URL,
                    HttpMethod.POST, plsrEntity,
                    String.class);

            log.info("Add Pickup Location Raw Response :: " + response.getBody());
            JsonNode jsonNode = mapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                //Update Data
                savePickUp.setResponsePacket(String.valueOf(jsonNode));
                savePickUp.setStatus("SUCCESS");
                this.pickUpAddressShipRocketRepo.save(savePickUp);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");

            } else {
                //MESSAGE RESPONSE
                messageResponse.put("raw", "FAILED");

                savePickUp.setStatus("FAILED");
                this.pickUpAddressShipRocketRepo.save(savePickUp);
                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (HttpClientErrorException e) {
            //MESSAGE-RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

            pickUpAddress_sr.setStatus("FAILED");
            pickUpAddress_sr.setResponsePacket(e.getMessage());
            this.pickUpAddressShipRocketRepo.save(pickUpAddress_sr);

        } catch (HttpServerErrorException e) {
            //MESSAGE-RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

            pickUpAddress_sr.setStatus("FAILED");
            pickUpAddress_sr.setResponsePacket(e.getMessage());
            this.pickUpAddressShipRocketRepo.save(pickUpAddress_sr);

        } catch (Exception e) {
            //MESSAGE-RESPONSE
            messageResponse.put("Error",e.getMessage());

            System.out.println("UNKNOWN ERROR");
            e.printStackTrace();

            pickUpAddress_sr.setStatus("FAILED");
            pickUpAddress_sr.setResponsePacket(e.getMessage());
            this.pickUpAddressShipRocketRepo.save(pickUpAddress_sr);
        }
        return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
    }


    @Override
    public ResponseEntity<?> orderCancelShipRocket(CancelOrderDtoShipRocket cancelOrderDtoShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CancelOrderRequestResponse_SR cancelOrderReqRes = new CancelOrderRequestResponse_SR();
        try {
            String validToken = this.getValidToken();
            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(validToken);

            HttpEntity<Object> httpEntity = new HttpEntity<>(cancelOrderDtoShipRocket, headers);
            String requestPacket = objectMapper.writeValueAsString(httpEntity);

            //save Data
            cancelOrderReqRes.setRequestPacket(requestPacket);
            CancelOrderRequestResponse_SR cancelOrderData = this.cancelOrderShipRocketRepo.save(cancelOrderReqRes);

            //CALLING API
            ResponseEntity<String> response = restTemplate.exchange(CANCEL_ORDER_URL, HttpMethod.POST, httpEntity, String.class);
            log.info("Cancel Order Response :: " + response.getBody());

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                //Update Data to DB
                cancelOrderData.setResponsePacket(String.valueOf(jsonNode));
                cancelOrderData.setStatus("SUCCESS");
                this.cancelOrderShipRocketRepo.save(cancelOrderData);

                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");
            } else {
                messageResponse.put("raw", jsonNode);
                //Update Data to DB
                cancelOrderData.setResponsePacket(String.valueOf(jsonNode));
                cancelOrderData.setStatus("FAILED");
                this.cancelOrderShipRocketRepo.save(cancelOrderData);

                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (HttpClientErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

            cancelOrderReqRes.setStatus("FAILED");
            cancelOrderReqRes.setResponsePacket(e.getMessage());
            this.cancelOrderShipRocketRepo.save(cancelOrderReqRes);

        } catch (HttpServerErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

            cancelOrderReqRes.setStatus("FAILED");
            cancelOrderReqRes.setResponsePacket(e.getMessage());
            this.cancelOrderShipRocketRepo.save(cancelOrderReqRes);

        } catch (Exception e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getMessage());

            System.out.println("UNKNOWN ERROR");
            e.printStackTrace();

            cancelOrderReqRes.setStatus("FAILED");
            cancelOrderReqRes.setResponsePacket(e.getMessage());
            this.cancelOrderShipRocketRepo.save(cancelOrderReqRes);

        }
        return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
    }


    public ResponseEntity<?> checkCourierAvailabilityShipRocket(CheckServiceAvailability checkServiceAvailability) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //ShipRocket Token if expiry to generate new token and expiry set before -5 min....
            String validToken = this.getValidToken();
            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = CHECK_SERVICE_AVAILABILITY
                    + "?order_id=" + checkServiceAvailability.getOrder_id()
                    + "&cod=" + checkServiceAvailability.getCod();
            ResponseEntity<String> response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    entity,
                    String.class);
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");
            } else {
                messageResponse.put("raw", jsonNode);
                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }

        } catch (HttpClientErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getMessage());

            System.out.println("UNKNOWN ERROR");
            messageResponse.put("Error",e.getMessage());
            e.printStackTrace();
        }
        return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
    }




    @Override
    public ResponseEntity<?> assignAwbNumberShipRocket(GenerateAwbNumberShipRocket generateAwbNumberShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //CHECK SHIP-ROCKET TOKEN
            String validToken = this.getValidToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + validToken);

            Long shipmentId = generateAwbNumberShipRocket.getShipmentId();
            Integer courierId = generateAwbNumberShipRocket.getCourierId();

            Map<String, Object> awbBody = new HashMap<>();
            awbBody.put("shipment_id", shipmentId);
            awbBody.put("courier_id", courierId);

            HttpEntity<Map<String, Object>> awbRequest = new HttpEntity<>(awbBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(GENERATE_AWB_NUMBER, awbRequest, String.class);
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");
            } else {
                messageResponse.put("raw", jsonNode);
                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (HttpClientErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

        } catch (Exception e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getMessage());

            System.out.println("UNKNOWN ERROR");
            messageResponse.put("Error",e.getMessage());
            e.printStackTrace();
        }
        return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
    }




    @Override
    public ResponseEntity<?> checkEstimateDeliveryTimeShipRocket1(CheckEstimateDeliveryTimeShipRocket
                                                                          checkEstimateDeliveryTimeShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //ShipRocket Token if expiry to generate new token and expiry set before -5 min....
            String validToken = this.getValidToken();

            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = CHECK_SERVICE_AVAILABILITY
                    + "?pickup_postcode=" + checkEstimateDeliveryTimeShipRocket.getPickup_postcode()
                    + "&delivery_postcode=" + checkEstimateDeliveryTimeShipRocket.getDelivery_postcode()
                    + "&cod=" + 0
                    + "&weight=" + checkEstimateDeliveryTimeShipRocket.getWeight()
                    + "&qc_check=" + checkEstimateDeliveryTimeShipRocket.getQc_check();


            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                JsonObject bestCourier = ShipRocketServiceHelper.getBestCourierPriceShipRocket(response.getBody());
                System.out.println("bestCourier: " + bestCourier);
                System.out.println("Courier Name: " + bestCourier.get("courier_name").getAsString());
                System.out.println("Courier ID: " + bestCourier.get("courier_company_id").getAsInt());
                System.out.println("Rate: " + bestCourier.get("rate").getAsDouble());
                System.out.println("Delivery Days: " + bestCourier.get("estimated_delivery_days").getAsString());
                System.out.println("etd: " + bestCourier.get("etd").getAsString());

                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");
            } else {
                messageResponse.put("raw", jsonNode);
                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (HttpClientErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

        } catch (Exception e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getMessage());

            System.out.println("UNKNOWN ERROR");
            messageResponse.put("Error",e.getMessage());
            e.printStackTrace();
        }
        return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
    }

    @Override
    public ResponseEntity<?> generateLabelShipRocket(List<String> shipmentIds) {

        Map<String, Object> messageResponse = new HashMap<>();

        try {
            String validToken = this.getValidToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // ✅ Body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("shipment_id", shipmentIds);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange( GENERATE_LABEL_URL,
                                                                    HttpMethod.POST,
                                                                    entity,
                                                                    String.class  );

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                JsonNode labelUrl = jsonNode.path("label_url");

                if (!labelUrl.isMissingNode()) {
                    messageResponse.put("label_url", labelUrl.asText());
                    System.out.println("Label URL: " + labelUrl.asText());
                }
                return ResponseGenerator.generateSuccessResponse(messageResponse, "SUCCESS");
            } else {
                messageResponse.put("raw", jsonNode);
                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (Exception e) {
            messageResponse.put("Error", e.getMessage());
            e.printStackTrace();
        }
        return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
    }




















































































//    ===============================================================================================================
//    ===============================================================================================================

    public JsonNode createOrderShipRocketAndValidToken(CreateOrderDtoShipRocket createOrderDtoShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CreateOrderRequestResponse_SR createOrderReqRes = new CreateOrderRequestResponse_SR();
        try {
            //ShipRocket Token if expiry to generate new token and expiry set before -5 min....
            String validToken = this.getValidToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(validToken);

            HttpEntity<Object> httpEntity = new HttpEntity<>(createOrderDtoShipRocket, headers);
            String requestPacket = objectMapper.writeValueAsString(httpEntity);

            //save Data
            createOrderReqRes.setRequestPacket(requestPacket);
            CreateOrderRequestResponse_SR orderData = this.createOrderShipRocketRepo.save(createOrderReqRes);

            //CALLING API
            ResponseEntity<String> response = restTemplate.exchange(CREATE_ORDER_URL, HttpMethod.POST, httpEntity, String.class);
            log.info("Create Ship Rocket Order Response :: " + response.getBody());

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                messageResponse.put("raw", jsonNode);
                //Update Data to DB
                orderData.setResponsePacket(String.valueOf(jsonNode));
                orderData.setStatus("SUCCESS");
                this.createOrderShipRocketRepo.save(orderData);

                return jsonNode;
            } else {
                messageResponse.put("raw", jsonNode);
                //Update Data to DB
                orderData.setResponsePacket(String.valueOf(jsonNode));
                orderData.setStatus("FAILED");
                this.createOrderShipRocketRepo.save(orderData);

                return jsonNode;
            }
        } catch (Exception e) {
            createOrderReqRes.setStatus("FAILED");
            createOrderReqRes.setResponsePacket(e.getMessage());
            this.createOrderShipRocketRepo.save(createOrderReqRes);

            e.printStackTrace();
            throw new RuntimeException("Error while creating ShipRocket order", e);
        }
    }


    public Map<String, String> checkCourierAvailabilityShipRocketFunction(CheckServiceAvailability checkServiceAvailability) {

        Map<String, String> finalResponseNode = new HashMap<>();
        try {
            //ShipRocket Token if expiry to generate new token and expiry set before -5 min....
            String validToken = this.getValidToken();
            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = CHECK_SERVICE_AVAILABILITY
                    + "?order_id=" + checkServiceAvailability.getOrder_id()
                    + "&cod=" + checkServiceAvailability.getCod();
            ResponseEntity<String> response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    entity,
                    String.class);

            // HTTP STATUS CHECK
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("CHECK_SERVICE_AVAILABILITY API SUCCESS --> STARTING");
                String responseBody = response.getBody();
                System.out.println("========== FULL COURIER LIST ==========");
                System.out.println(responseBody);
                System.out.println("=======================================");
                JsonObject bestRatingCourierRate = ShipRocketServiceHelper.getBestCourierPriceShipRocket(responseBody);

                System.out.println("=========== BEST COURIER SELECTED ===========");
                System.out.println("bestRatingCourierRate ==> " + bestRatingCourierRate);

                System.out.println("=========== SCRAPPING DATA BEST COURIER ===========");
                String courier_name = bestRatingCourierRate.get("courier_name").getAsString();
                String courier_company_id = bestRatingCourierRate.get("courier_company_id").getAsString();
                String rate = bestRatingCourierRate.get("rate").getAsString();
                String estimated_delivery_days = bestRatingCourierRate.get("estimated_delivery_days").getAsString();
                String etd = bestRatingCourierRate.get("etd").getAsString();
                System.out.println("Courier Name: " + courier_name);
                System.out.println("Courier Company ID: " + courier_company_id);
                System.out.println("Rate: " + rate);
                System.out.println("Estimated Delivery Days: " + estimated_delivery_days);
                System.out.println("ETD: " + etd);
                System.out.println("=========== SCRAPPING DATA PRINT ENDING ===========");
                System.out.println("CHECK_SERVICE_AVAILABILITY API SUCCESS --> ENDING...");


                System.out.println("=========================================================");
                System.out.println("GENERATE_AWB_NUMBER API SUCCESS --> STARTING...");
                GenerateAwbNumberShipRocket generateAwbNumberShipRocket = new GenerateAwbNumberShipRocket();
                generateAwbNumberShipRocket.setShipmentId(Long.parseLong(checkServiceAvailability.getShipment_id()));
                generateAwbNumberShipRocket.setCourierId(Integer.parseInt(courier_company_id));

                String generateAwbResponse = this.generateAwbNUmberShipRocket(generateAwbNumberShipRocket);
                System.out.println("generateAwbResponse RESPONSE --> " + generateAwbResponse);
                JsonObject obj = JsonParser.parseString(generateAwbResponse).getAsJsonObject();
                JsonObject courierData = obj.getAsJsonObject("response").getAsJsonObject("data");
                JsonObject shippedByData = courierData.getAsJsonObject("shipped_by");

                System.out.println("========= AWB STATUS =========");
                int awbStatus = obj.get("awb_assign_status").getAsInt();
                System.out.println("AWB STATUS: " + awbStatus);
                System.out.println("========= COURIER DATA =========");
                System.out.println("AWB Code: " + courierData.get("awb_code"));
                System.out.println("Courier Name: " + courierData.get("courier_name"));
                System.out.println("Courier Company ID: " + courierData.get("courier_company_id"));
                System.out.println("Order ID: " + courierData.get("order_id"));
                System.out.println("Shipment ID: " + courierData.get("shipment_id"));
                System.out.println("Pickup Date: " + courierData.get("pickup_scheduled_date"));
                System.out.println("Invoice No: " + courierData.get("invoice_no"));
                System.out.println("Applied Weight: " + courierData.get("applied_weight"));
                System.out.println("------- SHIPPING-BY DETAILS -------");
                System.out.println("Company Name: " + shippedByData.get("shipper_company_name"));
                System.out.println("City: " + shippedByData.get("shipper_city"));
                System.out.println("State: " + shippedByData.get("shipper_state"));
                System.out.println("Phone: " + shippedByData.get("shipper_phone"));
                System.out.println("Email: " + shippedByData.get("shipper_email"));
                System.out.println("GENERATE_AWB_NUMBER API SUCCESS --> ENDING...");


                System.out.println("=========================================================");
                System.out.println("ORDER DETAILS API --> STARTING...");

                String specificOrderDetails = this.getSpecificOrderDetails
                                                (Long.parseLong(checkServiceAvailability.getOrder_id()));

                System.out.println("ORDER DETAILS => " + specificOrderDetails );

                JsonObject specificDetails = JsonParser.parseString(specificOrderDetails).getAsJsonObject();
                JsonObject orderDetailsData = specificDetails.getAsJsonObject("data");
                JsonObject shipment = orderDetailsData.getAsJsonObject("shipments");

                String pickup_scheduled_date = shipment.get("pickup_scheduled_date").getAsString();
                String pickup_status = shipment.get("status").getAsString();
                String pickup_courier_name = shipment.get("courier").getAsString();
                String pickup_etd = shipment.get("etd").getAsString();

                System.out.println("Pickup Scheduled Date : " + pickup_scheduled_date);
                System.out.println("Pickup Status : " + pickup_status);
                System.out.println("PICKUP Courier Name : " + pickup_courier_name);
                System.out.println("PICKUP-ETD : " + pickup_etd);
                System.out.println("SERVICE AVAILABILITY COURIER NAME  : " + courier_name);
                System.out.println("SERVICE AVAILABILITY PICKUP-ETD : " + etd);



                finalResponseNode.put("RESULT", "SUCCESS");
                finalResponseNode.put("AWB", courierData.get("awb_code").getAsString());
                finalResponseNode.put("COURIER_NAME", bestRatingCourierRate.get("courier_name").getAsString());
                finalResponseNode.put("COURIER_ID", bestRatingCourierRate.get("courier_company_id").getAsString());
                finalResponseNode.put("RATE", bestRatingCourierRate.get("rate").getAsString());
                finalResponseNode.put("DELIVERY_DAYS", bestRatingCourierRate.get("etd").getAsString());
                finalResponseNode.put("ETD", etd);
                finalResponseNode.put("PICKUP_STATUS", pickup_status);
                finalResponseNode.put("PICKUP_SCHEDULE_DATE", pickup_scheduled_date);

                return finalResponseNode;

            } else {
                System.out.println("CHECK_SERVICE_AVAILABILITY API FAILED");
                System.out.println("Status Code : " + response.getStatusCode());
                System.out.println("Body : " + response.getBody());
            }
        } catch (HttpClientErrorException e) {
            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("UNKNOWN ERROR");
            e.printStackTrace();
        }
        finalResponseNode.put("RESULT", "FAILED");
        return finalResponseNode;
    }


    public String generateAwbNUmberShipRocket(GenerateAwbNumberShipRocket generateAwbNumberShipRocket) {
        try {
            //CHECK SHIP-ROCKET TOKEN
            String validToken = this.getValidToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + validToken);

            Long shipmentId = generateAwbNumberShipRocket.getShipmentId();
            Integer courierId = generateAwbNumberShipRocket.getCourierId();

            Map<String, Object> awbBody = new HashMap<>();
            awbBody.put("shipment_id", shipmentId);
            awbBody.put("courier_id", courierId);

            HttpEntity<Map<String, Object>> awbRequest = new HttpEntity<>(awbBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(GENERATE_AWB_NUMBER, awbRequest, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("GENERATE_AWB_NUMBER API SUCCESS --> STARTING");
                String responseBody = response.getBody();
                return responseBody;
            } else {
                System.out.println("GENERATE_AWB_NUMBER API FAILED");
                System.out.println("Status Code : " + response.getStatusCode());
                System.out.println("GENERATE_AWB_NUMBER DUMMY DATA  : " + ShipRocketServiceHelper.dispatchCourierDummyData);
                return ShipRocketServiceHelper.dispatchCourierDummyData;
            }
        } catch (HttpClientErrorException e) {
            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("UNKNOWN ERROR");
            e.getMessage();
            e.printStackTrace();
        }
        return ShipRocketServiceHelper.dispatchCourierDummyData;
    }





    public String getSpecificOrderDetails(Long orderId) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //ShipRocket Token if expiry to generate new token and expiry set before -5 min....
            String validToken = this.getValidToken();
            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = GET_SPECIFIC_ORDER_DETAILS + orderId;

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("SPECIFIC ORDER DETAILS API SUCCESS --> STARTING");
                String responseBody = response.getBody();
                return responseBody;
            } else {
                System.out.println("SPECIFIC ORDER DETAILS API FAILED");
                System.out.println("Status Code : " + response.getStatusCode());
                return null;
            }
        } catch (HttpClientErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getResponseBodyAsString());

            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());

        } catch (Exception e) {
            //MESSAGE RESPONSE
            messageResponse.put("Error",e.getMessage());

            System.out.println("UNKNOWN ERROR");
            messageResponse.put("Error",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }





    public String generatePickup(Long shipmentId) {
        try {
            //CHECK SHIP-ROCKET TOKEN
            String validToken = this.getValidToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + validToken);

            Map<String, Object> pickupBody = new HashMap<>();
            pickupBody.put("shipment_id", Collections.singletonList(shipmentId));
            HttpEntity<Map<String, Object>> pickupRequest = new HttpEntity<>(pickupBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(PICK_UP,
                    pickupRequest,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("PICKUP API SUCCESS --> STARTING");
                String responseBody = response.getBody();
                return responseBody;
            } else {
                System.out.println("PICKUP API FAILED");
                System.out.println("Status Code : " + response.getStatusCode());
                System.out.println("PICKUP DUMMY DATA  : " + ShipRocketServiceHelper.dispatchCourierDummyData);
                return ShipRocketServiceHelper.dispatchCourierDummyData;
            }
        } catch (HttpClientErrorException e) {
            System.out.println("CLIENT ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            System.out.println("SERVER ERROR");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Error Body : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("UNKNOWN ERROR");
            e.getMessage();
            e.printStackTrace();

        }
        return ShipRocketServiceHelper.pickupDummyResponse;
    }

}
