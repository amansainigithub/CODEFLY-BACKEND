package com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices.imple;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

public class ShipRocketServiceHelper {


    protected static LocalDateTime extractExpiry(String token) {

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

//    protected static JsonObject getBestCourier(String responseBody) {
//        JsonObject bestCourier = null;
//        try {
//            JsonParser parser = new JsonParser();
//            JsonObject json = parser.parse(responseBody).getAsJsonObject();
//
//            JsonArray couriers = json
//                    .getAsJsonObject("data")
//                    .getAsJsonArray("available_courier_companies");
//
//            double lowestRate = Double.MAX_VALUE;
//
//            for (JsonElement element : couriers) {
//                JsonObject courier = element.getAsJsonObject();
//                double rate = courier.get("rate").getAsDouble();
//                if (rate < lowestRate && courier.get("cod").getAsInt() == 1) {
//                    lowestRate = rate;
//                    bestCourier = courier;
//                }
//            }
//            System.out.println("===== BEST COURIER =====");
//            System.out.println(bestCourier);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bestCourier;
//    }


    protected static JsonObject getBestCourierPriceShipRocket(String responseBody) {

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
            ;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bestCourier;
    }


    protected static String dispatchCourierDummyData = "{\n" +
            "  \"awb_assign_status\": 1,\n" +
            "  \"response\": {\n" +
            "    \"data\": {\n" +
            "      \"courier_company_id\": 142,\n" +
            "      \"awb_code\": \"321055706540\",\n" +
            "      \"cod\": 0,\n" +
            "      \"order_id\": 281248157,\n" +
            "      \"shipment_id\": 16090281,\n" +
            "      \"awb_code_status\": 1,\n" +
            "      \"assigned_date_time\": {\n" +
            "        \"date\": \"2022-11-25 11:17:52.878599\",\n" +
            "        \"timezone_type\": 3,\n" +
            "        \"timezone\": \"Asia/Kolkata\"\n" +
            "      },\n" +
            "      \"applied_weight\": 0.5,\n" +
            "      \"company_id\": 25149,\n" +
            "      \"courier_name\": \"Amazon Surface\",\n" +
            "      \"child_courier_name\": null,\n" +
            "      \"pickup_scheduled_date\": \"2022-11-25 14:00:00\",\n" +
            "      \"routing_code\": \"\",\n" +
            "      \"rto_routing_code\": \"\",\n" +
            "      \"invoice_no\": \"retail5769122647118\",\n" +
            "      \"transporter_id\": \"\",\n" +
            "      \"transporter_name\": \"\",\n" +
            "      \"shipped_by\": {\n" +
            "        \"shipper_company_name\": \"manoj\",\n" +
            "        \"shipper_address_1\": \"Aligarh\",\n" +
            "        \"shipper_address_2\": \"noida\",\n" +
            "        \"shipper_city\": \"Jammu\",\n" +
            "        \"shipper_state\": \"Jammu & Kashmir\",\n" +
            "        \"shipper_country\": \"India\",\n" +
            "        \"shipper_postcode\": \"110030\",\n" +
            "        \"shipper_first_mile_activated\": 0,\n" +
            "        \"shipper_phone\": \"8976967989\",\n" +
            "        \"lat\": \"32.731899\",\n" +
            "        \"long\": \"74.860376\",\n" +
            "        \"shipper_email\": \"hdhd@gshd.com\",\n" +
            "        \"rto_company_name\": \"test\",\n" +
            "        \"rto_address_1\": \"Unnamed Road, Bengaluru, Karnataka 560060, India\",\n" +
            "        \"rto_address_2\": \"Katrabrahmpur\",\n" +
            "        \"rto_city\": \"Bangalore\",\n" +
            "        \"rto_state\": \"Karnataka\",\n" +
            "        \"rto_country\": \"India\",\n" +
            "        \"rto_postcode\": \"560060\",\n" +
            "        \"rto_phone\": \"9999999999\",\n" +
            "        \"rto_email\": \"test@test.com\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


    protected static String pickupDummyResponse = "{\n" +
            "  \"pickup_status\": 1,\n" +
            "  \"response\": {\n" +
            "    \"pickup_scheduled_date\": \"2021-12-10 12:39:54\",\n" +
            "    \"pickup_token_number\": \"Reference No: 194_BIGFOOT 1966840_11122021\",\n" +
            "    \"status\": 3,\n" +
            "    \"others\": \"{\\\"tier_id\\\":5,\\\"etd_zone\\\":\\\"z_e\\\",\\\"etd_hours\\\":\\\"{\\\\\\\"assign_to_pick\\\\\\\":6.9000000000000004,\\\\\\\"pick_to_ship\\\\\\\":22.600000000000001,\\\\\\\"ship_to_deliver\\\\\\\":151.40000000000001,\\\\\\\"etd_zone\\\\\\\":\\\\\\\"z_e\\\\\\\",\\\\\\\"pick_to_ship_table\\\\\\\":\\\\\\\"dev_etd_pickup_to_ship\\\\\\\",\\\\\\\"ship_to_deliver_table\\\\\\\":\\\\\\\"dev_etd_ship_to_deliver\\\\\\\"}\\\",\\\"actual_etd\\\":\\\"2021-12-18 00:36:03\\\",\\\"routing_code\\\":\\\"S2\\\\/S-69\\\\/1B\\\\/016\\\",\\\"addition_in_etd\\\":[\\\"deduction_of_6_and_half_hours\\\"],\\\"shipment_metadata\\\":{\\\"type\\\":\\\"ship\\\",\\\"device\\\":\\\"WebKit\\\",\\\"platform\\\":\\\"desktop\\\",\\\"client_ip\\\":\\\"94.237.77.195\\\",\\\"created_at\\\":\\\"2021-12-10 12:36:03\\\",\\\"request_type\\\":\\\"web\\\"},\\\"templatized_pricing\\\":0,\\\"selected_courier_type\\\":\\\"Best in price\\\",\\\"recommended_courier_data\\\":{\\\"etd\\\":\\\"Dec 19, 2021\\\",\\\"price\\\":153,\\\"rating\\\":3.6,\\\"courier_id\\\":54},\\\"recommendation_advance_rule\\\":null,\\\"dynamic_weight\\\":\\\"1.00\\\"}\",\n" +
            "    \"pickup_generated_date\": {\n" +
            "      \"date\": \"2021-12-10 12:39:54.034695\",\n" +
            "      \"timezone_type\": 3,\n" +
            "      \"timezone\": \"Asia/Kolkata\"\n" +
            "    },\n" +
            "    \"data\": \"Pickup is confirmed by Xpressbees 1kg For AWB :- 143254213727423\"\n" +
            "  }\n" +
            "}";


}
