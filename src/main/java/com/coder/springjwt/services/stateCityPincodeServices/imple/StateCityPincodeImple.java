package com.coder.springjwt.services.stateCityPincodeServices.imple;

import com.coder.springjwt.services.stateCityPincodeServices.StateCityPincodeService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StateCityPincodeImple implements StateCityPincodeService {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> getCityStatePincode(int pincode) {

        MessageResponse messageResponse = new MessageResponse();

        String apiUrl = "http://www.postalpincode.in/api/pincode/" +pincode;  // replace with actual API URL
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Handle response
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("API Fetch Success");
            String responseBody = response.getBody();

            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray postOffice = jsonObject.getJSONArray("PostOffice");
            JSONObject postOfficeObj = postOffice.getJSONObject(0);

            System.out.println("District:: " + postOfficeObj.getString("District"));
            System.out.println("State:: " + postOfficeObj.getString("State"));
            System.out.println("Country:: " + postOfficeObj.getString("Country"));

            Map<String,String> map = new HashMap();
            map.put("District" , postOfficeObj.getString("District"));
            map.put("State" , postOfficeObj.getString("State"));
            map.put("Country" , postOfficeObj.getString("Country"));

            messageResponse.setStatus(HttpStatus.OK);
            messageResponse.setMessage("API Fetch Success");
            messageResponse.setData(map);

            return ResponseGenerator.generateSuccessResponse(messageResponse,"Success");
        } else {
            messageResponse.setStatus(HttpStatus.BAD_REQUEST);
            messageResponse.setMessage("API Call Failure | please check API...");
            return ResponseGenerator.generateBadRequestResponse(messageResponse,"Failed");
        }

    }
}
