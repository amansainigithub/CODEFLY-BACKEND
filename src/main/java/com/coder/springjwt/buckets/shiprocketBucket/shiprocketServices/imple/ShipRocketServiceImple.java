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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    private static final String SHIP_ROCKET_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjY1MTI5NTMsInNvdXJjZSI6InNyLWF1dGgtaW50IiwiZXhwIjoxNzcyMTQzNDUzLCJqdGkiOiJGS2xib2Q4ck9lMlExZG9QIiwiaWF0IjoxNzcxMjc5NDUzLCJpc3MiOiJodHRwczovL3NyLWF1dGguc2hpcHJvY2tldC5pbi9hdXRob3JpemUvdXNlciIsIm5iZiI6MTc3MTI3OTQ1MywiY2lkIjo2Mjg3NDQ5LCJ0YyI6MzYwLCJ2ZXJib3NlIjpmYWxzZSwidmVuZG9yX2lkIjowLCJ2ZW5kb3JfY29kZSI6IiJ9.tiBc-ciB-p-wnU_y7QMUmHfCwc8N9uowZDdP9qpbPxY";
    private static final String SHIP_ROCKET_LOGIN_URL = "https://apiv2.shiprocket.in/v1/external/auth/login";
    private static final String NEW_PICKUP_LOCATION_URL = "https://apiv2.shiprocket.in/v1/external/settings/company/addpickup";
    private static final String CREATE_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/create/adhoc";
    private static final String CANCEL_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/cancel";
    private static final String CHECK_SERVICE_AVAILABILITY = "https://apiv2.shiprocket.in/v1/external/courier/serviceability/";
    private static final String DISPATCH_COURIER = "https://apiv2.shiprocket.in/v1/external/courier/assign/awb";

    private static final String PICK_UP = "https://apiv2.shiprocket.in/v1/external/courier/generate/pickup";
    @Override
    public ResponseEntity<?> generateTokenShipRocket(GenerateTokenDtoShipRocket generateTokenDtoShipRocket) {
        Map<String,Object> messageResponse = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        GenerateToken_SR generateToken_request = new GenerateToken_SR();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GenerateTokenDtoShipRocket> entity = new HttpEntity<>(generateTokenDtoShipRocket, headers);
            String requestJson = mapper.writeValueAsString(entity);

            //save Request Packet
            generateToken_request.setRequestPacket(requestJson);
            GenerateToken_SR saveTokenData = this.generateTokenSRRepository.save(generateToken_request);


            ResponseEntity<String> response = restTemplate.exchange(SHIP_ROCKET_LOGIN_URL,HttpMethod.POST,entity,String.class );
            log.info("Ship Rocket Raw Response :: " + response.getBody());


            JsonNode json = mapper.readTree(response.getBody());

            if (response.getStatusCode().is2xxSuccessful()  && json.has("token")) {

                messageResponse.put("token", json.get("token").asText());
                messageResponse.put("raw", json);

                //Update Response Packet
                saveTokenData.setResponsePacket(String.valueOf(json));
                saveTokenData.setStatus("SUCCESS");
                this.generateTokenSRRepository.save(saveTokenData);

                return ResponseGenerator.generateSuccessResponse(messageResponse , "SUCCESS");
            } else {
                messageResponse.put("error", json);

                //Update Response Packet
                saveTokenData.setResponsePacket(String.valueOf(json));
                saveTokenData.setStatus("FAILED");
                this.generateTokenSRRepository.save(saveTokenData);

                return ResponseGenerator.generateBadRequestResponse(messageResponse , "FAILED");
            }
        }
        catch (Exception e) {
            generateToken_request.setStatus("FAILED");
            this.generateTokenSRRepository.save(generateToken_request);
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }


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
        dto.setEmail("ishumicro07+1@gmail.com");
        dto.setPassword("4c#$CRZqlrWMo3nDb3UO74dDnX0g7MA#");

        JsonNode response = loginShipRocket(dto);

        String newToken = response.path("token").asText();

        if (newToken == null || newToken.isEmpty()) {
            throw new RuntimeException("Token not received from Shiprocket");
        }

        // 🔥 Expiry Extract
        LocalDateTime expiryTime = extractExpiry(newToken);

        ShipRocketToken entity = shipRocketTokenRepository.findAll()
                .stream()
                .findFirst()
                .orElse(new ShipRocketToken());

        entity.setToken(newToken);
        entity.setExpiryTime(expiryTime);

        shipRocketTokenRepository.save(entity);

        log.info("New token saved successfully");

        return newToken;
    }

    public JsonNode loginShipRocket(GenerateTokenDtoShipRocket dto) {

        try {

            log.info("Calling Shiprocket login API...");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GenerateTokenDtoShipRocket> entity =
                    new HttpEntity<>(dto, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    SHIP_ROCKET_LOGIN_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            log.info("Shiprocket login response status: {}", response.getStatusCode());

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Shiprocket login failed with status: "
                        + response.getStatusCode());
            }

            if (response.getBody() == null) {
                throw new RuntimeException("Shiprocket login returned empty body");
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.getBody());

            if (!json.has("token") || json.get("token").asText().isEmpty()) {
                throw new RuntimeException("Token not found in Shiprocket response");
            }

            log.info("Shiprocket token received successfully");

            return json;

        } catch (HttpClientErrorException ex) {

            log.error("Shiprocket login client error: {}", ex.getResponseBodyAsString());
            throw new RuntimeException("Shiprocket login client error", ex);

        } catch (HttpServerErrorException ex) {

            log.error("Shiprocket login server error: {}", ex.getResponseBodyAsString());
            throw new RuntimeException("Shiprocket login server error", ex);

        } catch (Exception e) {

            log.error("Unexpected error during Shiprocket login", e);
            throw new RuntimeException("Shiprocket login error", e);
        }
    }




    private LocalDateTime extractExpiry(String token) {

        try {
            String[] parts = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(payload);

            long exp = jsonNode.get("exp").asLong();

            return Instant.ofEpochSecond(exp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract token expiry", e);
        }
    }



    @Override
    public ResponseEntity<?> createOrderShipRocket(CreateOrderDtoShipRocket createOrderDtoShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CreateOrderRequestResponse_SR createOrderReqRes = new CreateOrderRequestResponse_SR();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(SHIP_ROCKET_TOKEN);

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
                messageResponse.put("raw", jsonNode);
                //Update Data to DB
                orderData.setResponsePacket(String.valueOf(jsonNode));
                orderData.setStatus("FAILED");
                this.createOrderShipRocketRepo.save(orderData);

                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (Exception e) {
            createOrderReqRes.setStatus("FAILED");
            createOrderReqRes.setResponsePacket(e.getMessage());
            this.createOrderShipRocketRepo.save(createOrderReqRes);

            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage(), "FAILED");
        }
    }


    @Override
    public ResponseEntity<?> addPickupLocationShipRocket(PickUpLocationDtoShipRocket pickUpLocationDtoShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        PickUpAddress_SR pickUpAddress_sr = new PickUpAddress_SR();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // 🔥 HARD-CODED TOKEN
            headers.setBearerAuth(SHIP_ROCKET_TOKEN);

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

                savePickUp.setStatus("FAILED");
                this.pickUpAddressShipRocketRepo.save(savePickUp);
                return ResponseGenerator.generateBadRequestResponse(messageResponse, "FAILED");
            }
        } catch (Exception e) {
            pickUpAddress_sr.setResponsePacket(e.getMessage());
            pickUpAddress_sr.setStatus("FAILED");
            this.pickUpAddressShipRocketRepo.save(pickUpAddress_sr);

            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("FAILED");
        }
    }





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
    @Override
    public ResponseEntity<?> orderCancelShipRocket(CancelOrderDtoShipRocket cancelOrderDtoShipRocket) {
        Map<String, Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CancelOrderRequestResponse_SR cancelOrderReqRes = new CancelOrderRequestResponse_SR();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(SHIP_ROCKET_TOKEN);

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
        } catch (Exception e) {
            cancelOrderReqRes.setStatus("FAILED");
            cancelOrderReqRes.setResponsePacket(e.getMessage());
            this.cancelOrderShipRocketRepo.save(cancelOrderReqRes);

            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage(), "FAILED");
        }
    }

    @Override
    public ResponseEntity<?> checkCourierAvailabilityShipRocket(CheckServiceAvailability checkServiceAvailability) {
        try {

            String url = CHECK_SERVICE_AVAILABILITY
                    + "?order_id=" + checkServiceAvailability.getOrder_id()
                    + "&cod=" + checkServiceAvailability.getCod();

            //ShipRocket Token if expiry to generate new token and expiry set before -5 min....
            String validToken = this.getValidToken();

            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            String.class
                    );

            String responseBody = response.getBody();

            // Convert String to JSON Object
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonResponse = objectMapper.readValue(responseBody, Object.class);

            // 🔥 Console me full response print
            System.out.println("========== FULL COURIER LIST ==========");
            System.out.println(responseBody);
            System.out.println("=======================================");

            return ResponseGenerator.generateSuccessResponse(jsonResponse,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    @Override
    public ResponseEntity<?> dispatchCourierShipRocket(DispatchCourierShipRocket dispatchCourierShipRocket) {
        try {

            // 1️⃣ Get Valid Token
            String validToken = this.getValidToken();

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + validToken);

            Long shipmentId = dispatchCourierShipRocket.getShipmentId();
            Integer courierId = dispatchCourierShipRocket.getCourierId();

            // =========================
            // ✅ STEP 1: ASSIGN AWB
            // =========================
            Map<String, Object> awbBody = new HashMap<>();
            awbBody.put("shipment_id", shipmentId);
            awbBody.put("courier_id", courierId);

            HttpEntity<Map<String, Object>> awbRequest =
                    new HttpEntity<>(awbBody, headers);

            ResponseEntity<String> awbResponse = restTemplate.postForEntity(
                    DISPATCH_COURIER,
                    awbRequest,
                    String.class
            );

            if (!awbResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("AWB Generation Failed: " + awbResponse.getBody());
            }

            // =========================
            // ✅ STEP 2: GENERATE PICKUP
            // =========================
//            Map<String, Object> pickupBody = new HashMap<>();
//            pickupBody.put("shipment_id", Collections.singletonList(shipmentId));
//
//            HttpEntity<Map<String, Object>> pickupRequest =
//                    new HttpEntity<>(pickupBody, headers);
//
//            ResponseEntity<String> pickupResponse = restTemplate.postForEntity(
//                    PICK_UP,
//                    pickupRequest,
//                    String.class
//            );
//
//            if (!pickupResponse.getStatusCode().is2xxSuccessful()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("Pickup Generation Failed: " + pickupResponse.getBody());
//            }

            // =========================
            // ✅ FINAL RESPONSE
            // =========================
            Map<String, Object> finalResponse = new HashMap<>();
            finalResponse.put("awbResponse", awbResponse.getBody());
           // finalResponse.put("pickupResponse", pickupResponse.getBody());

            return ResponseGenerator.generateSuccessResponse(finalResponse,"SUCCESS");

        } catch (Exception e) {
            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Dispatch Failed: " + e.getMessage());
            return ResponseGenerator.generateBadRequestResponse("Dispatch Failed: " +e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> checkEstimateDeliveryTimeShipRocket(CheckEstimateDeliveryTimeShipRocket checkEstimateDeliveryTimeShipRocket) {
        try {

            String url = CHECK_SERVICE_AVAILABILITY
                    + "?pickup_postcode=" + checkEstimateDeliveryTimeShipRocket.getPickup_postcode()
                    + "&delivery_postcode=" + checkEstimateDeliveryTimeShipRocket.getDelivery_postcode()
                    + "&cod=" + 0
                    + "&weight=" + checkEstimateDeliveryTimeShipRocket.getWeight()
                    + "&qc_check=" + checkEstimateDeliveryTimeShipRocket.getQc_check();

            //ShipRocket Token if expiry to generate new token and expiry set before -5 min....
            String validToken = this.getValidToken();

            log.info("Ship Rocket token :: " + validToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response =restTemplate.exchange( url,HttpMethod.GET,entity,String.class );

            String responseBody = response.getBody();

            // 🔥 Console me full response print
            System.out.println("========== FULL COURIER LIST ==========");
            System.out.println(responseBody);
            System.out.println("=======================================");

            JsonObject bestCourier = getBestCourier(responseBody);

            System.out.println("Courier Name: " + bestCourier.get("courier_name").getAsString());
            System.out.println("Courier ID: " + bestCourier.get("courier_company_id").getAsInt());
            System.out.println("Rate: " + bestCourier.get("rate").getAsDouble());
            System.out.println("Delivery Days: " + bestCourier.get("estimated_delivery_days").getAsString());
            System.out.println("etd: " + bestCourier.get("etd").getAsString());



            return ResponseGenerator.generateSuccessResponse(
                            Map.of("Courier Name", bestCourier.get("courier_name").getAsString(),
                        "Courier ID", bestCourier.get("courier_company_id").getAsInt(),
                        "Rate", bestCourier.get("rate").getAsDouble(),
                        "Delivery Days: ", bestCourier.get("estimated_delivery_days").getAsString(),
                        "etd: ", bestCourier.get("estimated_delivery_days").getAsString()) ,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }


    public JsonObject getBestCourier(String responseBody) {

        JsonObject bestCourier = null;

        try {

            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(responseBody).getAsJsonObject();

            JsonArray couriers = json
                    .getAsJsonObject("data")
                    .getAsJsonArray("available_courier_companies");

            double lowestRate = Double.MAX_VALUE;

            for (JsonElement element : couriers) {

                JsonObject courier = element.getAsJsonObject();

                double rate = courier.get("rate").getAsDouble();

                if (rate < lowestRate && courier.get("cod").getAsInt() == 1) {
                    lowestRate = rate;
                    bestCourier = courier;
                }
            }

            System.out.println("===== BEST COURIER =====");
            System.out.println(bestCourier);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bestCourier;
    }


}
