package com.coder.springjwt.services.MobileOtpService.imple;

import com.coder.springjwt.exception.customerException.PropsNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.props.Api_Props;
import com.coder.springjwt.models.props.OtpRequestResponse;
import com.coder.springjwt.repository.OtpReqResRepository.OtpRequestResponseRepo;
import com.coder.springjwt.repository.apiProps.ApiPropsRepository;
import com.coder.springjwt.services.MobileOtpService.MobileOtpService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class OtpServiceImple implements MobileOtpService {
    Logger logger  = LoggerFactory.getLogger(OtpServiceImple.class);
    @Autowired
    private ApiPropsRepository apiPropsRepository;
    @Autowired
    OtpRequestResponseRepo otpRequestResponseRepo;
    private static String LANGUAGE = "english";
    private static String ROUTE = "q";


    public  void sendSMS(String number , String messageContent , String userRole , String areaMode) throws IOException {
        logger.info("Starting Messaging Service");

        //get UserName
        Map<String,String> node =  UserHelper.getCurrentUser();
        String username =  node.get("username");

        //Get API Properties
        Api_Props props = this.apiPropsRepository.findByProvider("FAST2SMS");
        if(props == null)
        {
            //Save Data to Props
            this.savePropsRequestResponse(number,userRole , areaMode , username);

            logger.error("Props Not Found :" +OtpServiceImple.class.getName());
            throw new PropsNotFoundException("Props Not Found " + "`FAST2SMS`");
        }else{

            String encodeMessage = OtpServiceImple.encodeMessage(messageContent);
            String smsUrl = props.getApiUrl()+ props.getApiKey()
                            +"&message="+encodeMessage+"&language="+LANGUAGE+"&route="+ROUTE+"&numbers="+number+"";
        try {
                URL url = new URL(smsUrl);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                int code = httpsURLConnection.getResponseCode();
                logger.info("Status Code:: " + code);

                if(code == 200){
                    StringBuffer response = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        } else {
                            response.append(line);
                        }
                    }
                    logger.info("OTP send Successfully");
                    this.saveSuccessRequestResponse(number,response.toString() ,code,smsUrl , userRole , areaMode , username);
                    logger.info("OTP saveSuccessRequestResponse Save Success");

            }else{
                this.saveErrorRequestResponse(number,code,smsUrl , userRole , areaMode , username);
            }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Message Not Sent ! Error in FastSMS API " + this.getClass().getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String encodeMessage(String message)
    {
        try {
           return  URLEncoder.encode(message,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public void savePropsRequestResponse(String number , String userRole , String areaMode , String username)
    {
        JSONObject jsonNode = new JSONObject();
        try {
            OtpRequestResponse otpRequestResponse = new OtpRequestResponse();
            //Set Message to save data in Database
            jsonNode.put("code","500");
            jsonNode.put("status","failed");
            jsonNode.put("message","API Props Error and FAST2SMS Key not Matched in DB--");

            //Set AreaMode and CurrentUser
            otpRequestResponse.setUserName(username);
            otpRequestResponse.setAreaMode(areaMode);

            //Set data to save Request Response Table
            otpRequestResponse.setStatus("FAILED");
            otpRequestResponse.setMessage("Internal Server Error");
            otpRequestResponse.setStatusCode("500");
            otpRequestResponse.setMobileNumber(number);
            otpRequestResponse.setUserRole("SELLER");
            otpRequestResponse.setRequest(jsonNode.toString());
            otpRequestResponse.setResponse(jsonNode.toString());
            //save Data To OTP Request Response Table
            this.otpRequestResponseRepo.save(otpRequestResponse);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveSuccessRequestResponse(String number , String response , int code ,String smsUrl ,
                                           String userRole , String areaMode, String username)
    {

        try {
            OtpRequestResponse otpRequestResponse = new OtpRequestResponse();
            JSONObject jsonObject = new JSONObject(response.toString());

            //Set User and Mode
            otpRequestResponse.setAreaMode(areaMode);
            otpRequestResponse.setUserName(username);


            //Set data to save Request Response Table
            otpRequestResponse.setStatus("SUCCESS");
            otpRequestResponse.setMessage("Message Sent Success");
            otpRequestResponse.setStatusCode(String.valueOf(code));
            otpRequestResponse.setMobileNumber(number);
            otpRequestResponse.setUserRole(userRole);
            otpRequestResponse.setRequest(smsUrl);
            otpRequestResponse.setResponse(jsonObject.toString());
            //save Data To OTP Request Response Table
            this.otpRequestResponseRepo.save(otpRequestResponse);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveErrorRequestResponse(String number, int code ,String smsUrl , String userRole , String areaMode , String username)
    {
        JSONObject jsonNode = new JSONObject();
        try {
            OtpRequestResponse otpRequestResponse = new OtpRequestResponse();
            //Set Message to save data in Database
            jsonNode.put("code","401");
            jsonNode.put("status","failed");
            jsonNode.put("message","Invalid Request Parameters:");

            //Set data to save Request Response Table
            otpRequestResponse.setStatus("FAILED");
            otpRequestResponse.setMessage("Invalid Request Parameters:");
            otpRequestResponse.setStatusCode(String.valueOf(code));
            otpRequestResponse.setMobileNumber(number);
            otpRequestResponse.setUserRole(userRole);
            otpRequestResponse.setRequest(smsUrl);
            otpRequestResponse.setResponse(jsonNode.toString());
            //save Data To OTP Request Response Table
            this.otpRequestResponseRepo.save(otpRequestResponse);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }




}
