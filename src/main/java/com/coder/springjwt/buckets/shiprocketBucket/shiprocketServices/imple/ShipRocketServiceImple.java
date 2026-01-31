package com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices.imple;

import com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo.CancelOrderShipRocketRepo;
import com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo.CreateOrderShipRocketRepo;
import com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo.GenerateTokenShipRocketRepo;
import com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo.PickUpAddressShipRocketRepo;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.GenerateTokenDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.PickUpLocationDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.CancelOrderDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.CreateOrderDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.CancelOrderRequestResponse_SR;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.CreateOrderRequestResponse_SR;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.GenerateToken_SR;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.PickUpAddress_SR;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices.ShipRocketService;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private static final String SHIP_ROCKET_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjg4MjExMDEsInNvdXJjZSI6InNyLWF1dGgtaW50IiwiZXhwIjoxNzY2NTU0ODUxLCJqdGkiOiIzRkFwTUJaaTBWRzFJYU1VIiwiaWF0IjoxNzY1NjkwODUxLCJpc3MiOiJodHRwczovL3NyLWF1dGguc2hpcHJvY2tldC5pbi9hdXRob3JpemUvdXNlciIsIm5iZiI6MTc2NTY5MDg1MSwiY2lkIjo1MzY5NDM2LCJ0YyI6MzYwLCJ2ZXJib3NlIjpmYWxzZSwidmVuZG9yX2lkIjowLCJ2ZW5kb3JfY29kZSI6IiJ9.w9I7IecZeKk05j9KoGCLFz2EuzbsZWvMSQW6XqMQN2o";
    private static final String SHIP_ROCKET_LOGIN_URL = "https://apiv2.shiprocket.in/v1/external/auth/login";
    private static final String NEW_PICKUP_LOCATION_URL = "https://apiv2.shiprocket.in/v1/external/settings/company/addpickup";
    private static final String CREATE_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/create/adhoc";
    private static final String CANCEL_ORDER_URL = "https://apiv2.shiprocket.in/v1/external/orders/cancel";


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









    @Override
    public ResponseEntity<?> addPickupLocationShipRocket(PickUpLocationDtoShipRocket pickUpLocationDtoShipRocket) {
        Map<String,Object> messageResponse = new HashMap<>();
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

            ResponseEntity<String> response = restTemplate.exchange( NEW_PICKUP_LOCATION_URL,
                                                                    HttpMethod.POST, plsrEntity,
                                                                    String.class);

            log.info("Add Pickup Location Raw Response :: " + response.getBody());
            JsonNode jsonNode = mapper.readTree(response.getBody());

            if(response.getStatusCode().is2xxSuccessful())
            {
               messageResponse.put("raw",jsonNode);

               //Update Data
               savePickUp.setResponsePacket(String.valueOf(jsonNode));
               savePickUp.setStatus("SUCCESS");
               this.pickUpAddressShipRocketRepo.save(savePickUp);
               return ResponseGenerator.generateSuccessResponse(messageResponse , "SUCCESS");

            }else{

                savePickUp.setStatus("FAILED");
                this.pickUpAddressShipRocketRepo.save(savePickUp);
                return ResponseGenerator.generateBadRequestResponse(messageResponse , "FAILED");
            }
        }
        catch (Exception e)
        {
            pickUpAddress_sr.setResponsePacket(e.getMessage());
            pickUpAddress_sr.setStatus("FAILED");
            this.pickUpAddressShipRocketRepo.save(pickUpAddress_sr);

            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("FAILED");
        }
    }







    @Override
    public ResponseEntity<?> createOrderShipRocket(CreateOrderDtoShipRocket createOrderDtoShipRocket) {
        Map<String,Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CreateOrderRequestResponse_SR createOrderReqRes= new CreateOrderRequestResponse_SR();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(SHIP_ROCKET_TOKEN);

            HttpEntity<Object> httpEntity = new HttpEntity<>(createOrderDtoShipRocket ,headers);
            String requestPacket = objectMapper.writeValueAsString(httpEntity);

            //save Data
            createOrderReqRes.setRequestPacket(requestPacket);
            CreateOrderRequestResponse_SR orderData = this.createOrderShipRocketRepo.save(createOrderReqRes);

            //CALLING API
            ResponseEntity<String> response = restTemplate.exchange(CREATE_ORDER_URL, HttpMethod.POST, httpEntity, String.class);
            log.info("Create Ship Rocket Order Response :: " + response.getBody());

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if(response.getStatusCode().is2xxSuccessful())
            {
                messageResponse.put("raw",jsonNode);
                //Update Data to DB
                orderData.setResponsePacket(String.valueOf(jsonNode));
                orderData.setStatus("SUCCESS");
                this.createOrderShipRocketRepo.save(orderData);

                return ResponseGenerator.generateSuccessResponse(messageResponse,"SUCCESS");
            }else{
                messageResponse.put("raw",jsonNode);
                //Update Data to DB
                orderData.setResponsePacket(String.valueOf(jsonNode));
                orderData.setStatus("FAILED");
                this.createOrderShipRocketRepo.save(orderData);

                return ResponseGenerator.generateBadRequestResponse(messageResponse,"FAILED");
            }
        }
        catch (Exception e)
        {
            createOrderReqRes.setStatus("FAILED");
            createOrderReqRes.setResponsePacket(e.getMessage());
            this.createOrderShipRocketRepo.save(createOrderReqRes);

            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "FAILED");
        }
    }

    @Override
    public ResponseEntity<?> orderCancelShipRocket(CancelOrderDtoShipRocket cancelOrderDtoShipRocket) {
        Map<String,Object> messageResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CancelOrderRequestResponse_SR cancelOrderReqRes= new CancelOrderRequestResponse_SR();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(SHIP_ROCKET_TOKEN);

            HttpEntity<Object> httpEntity = new HttpEntity<>(cancelOrderDtoShipRocket ,headers);
            String requestPacket = objectMapper.writeValueAsString(httpEntity);

            //save Data
            cancelOrderReqRes.setRequestPacket(requestPacket);
            CancelOrderRequestResponse_SR cancelOrderData = this.cancelOrderShipRocketRepo.save(cancelOrderReqRes);

            //CALLING API
            ResponseEntity<String> response = restTemplate.exchange(CANCEL_ORDER_URL, HttpMethod.POST, httpEntity, String.class);
            log.info("Cancel Order Response :: " + response.getBody());

            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            if(response.getStatusCode().is2xxSuccessful())
            {
                messageResponse.put("raw",jsonNode);
                //Update Data to DB
                cancelOrderData.setResponsePacket(String.valueOf(jsonNode));
                cancelOrderData.setStatus("SUCCESS");
                this.cancelOrderShipRocketRepo.save(cancelOrderData);

                return ResponseGenerator.generateSuccessResponse(messageResponse,"SUCCESS");
            }else{
                messageResponse.put("raw",jsonNode);
                //Update Data to DB
                cancelOrderData.setResponsePacket(String.valueOf(jsonNode));
                cancelOrderData.setStatus("FAILED");
                this.cancelOrderShipRocketRepo.save(cancelOrderData);

                return ResponseGenerator.generateBadRequestResponse(messageResponse,"FAILED");
            }
        }
        catch (Exception e)
        {
            cancelOrderReqRes.setStatus("FAILED");
            cancelOrderReqRes.setResponsePacket(e.getMessage());
            this.cancelOrderShipRocketRepo.save(cancelOrderReqRes);

            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "FAILED");
        }
    }








}
