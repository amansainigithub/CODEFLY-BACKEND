package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostService.imple;

import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusPostCancelShipmentRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusPostLoginRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.NimbusServiceabilityRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.shipment.NimbusPostShipmentRequest;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostModel.NimbusPostApiLog;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostRepo.NimbusPostApiRepository;
import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostService.NimbusPostService;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NimbusPostServiceImple implements NimbusPostService {

    @Autowired
    private NimbusPostApiRepository nimbusPostApiRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOGIN_URL = "https://api.nimbuspost.com/v1/users/login";
    private static final String SHIPMENT_URL = "https://api.nimbuspost.com/v1/shipments";
    private static final String SHIPMENT_CANCEL_URL = "https://api.nimbuspost.com/v1/shipments/cancel";
    private static final String SHIPMENT_TRACK_URL = "https://api.nimbuspost.com/v1/shipments/track/{awb}";
    private static final String SERVICEABILITY_URL = "https://api.nimbuspost.com/v1/courier/serviceability";
    @Override
    public ResponseEntity<?> generateTokenNimbusPost(NimbusPostLoginRequest nimbusPostLoginRequest) {
        NimbusPostApiLog nimbusPostApiLog = new NimbusPostApiLog();
        try {
            // Save request
            ObjectMapper mapper = new ObjectMapper();
            nimbusPostApiLog.setRequestPayload(mapper.writeValueAsString(nimbusPostLoginRequest));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<NimbusPostLoginRequest> entity = new HttpEntity<>(nimbusPostLoginRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, entity, String.class);

            // Save response
            nimbusPostApiLog.setResponsePayload(response.getBody());
            nimbusPostApiLog.setHttpStatus(response.getStatusCode().value());

            if (response.getStatusCode().is2xxSuccessful()) {
                nimbusPostApiLog.setStatus("SUCCESS");
                nimbusPostApiRepository.save(nimbusPostApiLog);
                return ResponseGenerator.generateSuccessResponse(response.getBody(), "SUCCESS");
            } else {
                nimbusPostApiLog.setStatus("FAILURE");
                nimbusPostApiRepository.save(nimbusPostApiLog);
                return ResponseGenerator.generateBadRequestResponse(response.getBody(), "FAILURE");
            }
        } catch (Exception e) {
            e.printStackTrace();

            nimbusPostApiLog.setStatus("ERROR");
            nimbusPostApiLog.setResponsePayload(e.getMessage());
            nimbusPostApiRepository.save(nimbusPostApiLog);

            return ResponseGenerator.generateBadRequestResponse("FAILURE");
        }
    }

    @Override
    public ResponseEntity<?> createShipmentNimbusPost(NimbusPostShipmentRequest nimbusPostShipmentRequest, String token) {
        NimbusPostApiLog nimbusPostApiLog = new NimbusPostApiLog();

        try {
            ObjectMapper mapper = new ObjectMapper();
            nimbusPostApiLog.setRequestPayload(mapper.writeValueAsString(nimbusPostShipmentRequest));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<NimbusPostShipmentRequest> entity =new HttpEntity<>(nimbusPostShipmentRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(SHIPMENT_URL,HttpMethod.POST,entity,String.class);

            nimbusPostApiLog.setResponsePayload(response.getBody());
            nimbusPostApiLog.setHttpStatus(response.getStatusCode().value());

            if (response.getStatusCode().is2xxSuccessful()) {
                nimbusPostApiLog.setStatus("SUCCESS");
                nimbusPostApiRepository.save(nimbusPostApiLog);
                return ResponseGenerator.generateSuccessResponse(response.getBody(), "SHIPMENT_CREATED");
            } else {
                nimbusPostApiLog.setStatus("FAILURE");
                nimbusPostApiRepository.save(nimbusPostApiLog);
                return ResponseGenerator.generateBadRequestResponse(response.getBody(), "SHIPMENT_FAILED");
            }

        } catch (Exception e) {
            e.printStackTrace();
            nimbusPostApiLog.setStatus("ERROR");
            nimbusPostApiLog.setResponsePayload(e.getMessage());
            nimbusPostApiRepository.save(nimbusPostApiLog);
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    @Override
    public ResponseEntity<?> cancelShipmentNimbusPost(NimbusPostCancelShipmentRequest nimbusPostCancelShipmentRequest, String token) {
        NimbusPostApiLog nimbusPostApiLog = new NimbusPostApiLog();

        try {
            ObjectMapper mapper = new ObjectMapper();
            nimbusPostApiLog.setRequestPayload(mapper.writeValueAsString(nimbusPostCancelShipmentRequest));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<NimbusPostCancelShipmentRequest> entity = new HttpEntity<>(nimbusPostCancelShipmentRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange( SHIPMENT_CANCEL_URL, HttpMethod.POST,entity,String.class);

            nimbusPostApiLog.setResponsePayload(response.getBody());
            nimbusPostApiLog.setHttpStatus(response.getStatusCode().value());

            if (response.getStatusCode().is2xxSuccessful()) {
                nimbusPostApiLog.setStatus("SUCCESS");
                nimbusPostApiRepository.save(nimbusPostApiLog);
                return ResponseGenerator.generateSuccessResponse(response.getBody(),"SHIPMENT_CANCELLED");
            } else {
                nimbusPostApiLog.setStatus("FAILURE");
                nimbusPostApiRepository.save(nimbusPostApiLog);
                return ResponseGenerator.generateBadRequestResponse(response.getBody(),"SHIPMENT_CANCEL_FAILED");
            }

        } catch (Exception e) {
            e.printStackTrace();
            nimbusPostApiLog.setStatus("ERROR");
            nimbusPostApiLog.setResponsePayload(e.getMessage());
            nimbusPostApiRepository.save(nimbusPostApiLog);
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    @Override
    public ResponseEntity<?> trackShipmentNimbusPost(String awb, String token) {

        NimbusPostApiLog nimbusPostApiLog = new NimbusPostApiLog();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(SHIPMENT_TRACK_URL,HttpMethod.GET,entity,String.class,awb);

            // RESPONSE PAYLOAD
            nimbusPostApiLog.setResponsePayload(response.getBody());
            nimbusPostApiLog.setHttpStatus(response.getStatusCode().value());

            if (response.getStatusCode().is2xxSuccessful()) {
                nimbusPostApiLog.setStatus("SUCCESS");
                nimbusPostApiRepository.save(nimbusPostApiLog);

                return ResponseGenerator.generateSuccessResponse(
                        response.getBody(), "SUCCESS"
                );
            } else {
                nimbusPostApiLog.setStatus("FAILURE");
                nimbusPostApiRepository.save(nimbusPostApiLog);

                return ResponseGenerator.generateBadRequestResponse(
                        response.getBody(), "FAILED"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            nimbusPostApiLog.setStatus("ERROR");
            nimbusPostApiLog.setResponsePayload(e.getMessage());
            nimbusPostApiRepository.save(nimbusPostApiLog);

            return ResponseGenerator.generateBadRequestResponse();
        }
    }


    @Override
    public ResponseEntity<?> serviceAbilityNimbusPost(NimbusServiceabilityRequest nimbusServiceabilityRequest,
                                                      String token)
    {
        NimbusPostApiLog nimbusPostApiLog = new NimbusPostApiLog();

        try {
            ObjectMapper mapper = new ObjectMapper();

            // SAVE REQUEST
            nimbusPostApiLog.setRequestPayload(mapper.writeValueAsString(nimbusServiceabilityRequest));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<NimbusServiceabilityRequest> entity =
                    new HttpEntity<>(nimbusServiceabilityRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(SERVICEABILITY_URL,HttpMethod.POST,entity,String.class );

            // SAVE RESPONSE
            nimbusPostApiLog.setResponsePayload(response.getBody());
            nimbusPostApiLog.setHttpStatus(response.getStatusCode().value());

            if (response.getStatusCode().is2xxSuccessful()) {
                nimbusPostApiLog.setStatus("SUCCESS");
                nimbusPostApiRepository.save(nimbusPostApiLog);

                return ResponseGenerator.generateSuccessResponse(response.getBody(), "SUCCESS" );
            } else {
                nimbusPostApiLog.setStatus("FAILURE");
                nimbusPostApiRepository.save(nimbusPostApiLog);
                return ResponseGenerator.generateBadRequestResponse(response.getBody(), "FAILURE");
            }

        } catch (Exception e) {
            e.printStackTrace();

            nimbusPostApiLog.setStatus("ERROR");
            nimbusPostApiLog.setResponsePayload(e.getMessage());
            nimbusPostApiRepository.save(nimbusPostApiLog);

            return ResponseGenerator.generateBadRequestResponse();
        }
    }



}
