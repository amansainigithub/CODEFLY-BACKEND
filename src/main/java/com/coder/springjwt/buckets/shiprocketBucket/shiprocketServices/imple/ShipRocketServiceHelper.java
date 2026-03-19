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



    protected static String specificOrderDetails = "{\n" +
            "    \"data\": {\n" +
            "        \"id\": 1235119992,\n" +
            "        \"channel_id\": 5917595,\n" +
            "        \"last_mile_awb\": null,\n" +
            "        \"last_mile_awb_track_url\": null,\n" +
            "        \"last_mile_courier_name\": null,\n" +
            "        \"channel_name\": \"CUSTOM\",\n" +
            "        \"base_channel_code\": \"CS\",\n" +
            "        \"is_international\": 0,\n" +
            "        \"is_document\": 0,\n" +
            "        \"channel_order_id\": \"141765\",\n" +
            "        \"customer_name\": \"Aman Suryavanshi\",\n" +
            "        \"customer_email\": \"test@gmail.com\",\n" +
            "        \"customer_phone\": \"xxxxxxxxxx\",\n" +
            "        \"customer_address\": \"Samrat Vikram Colony near Petrol 248001 Delhi\",\n" +
            "        \"customer_address_2\": \"near HP Petrol\",\n" +
            "        \"customer_city\": \"East Delhi\",\n" +
            "        \"rto_risk\": \"low\",\n" +
            "        \"address_risk\": \"NA\",\n" +
            "        \"order_risk\": \"low\",\n" +
            "        \"address_score\": \"0\",\n" +
            "        \"address_category\": \"unable_to_predict\",\n" +
            "        \"rto_show\": 0,\n" +
            "        \"customer_state\": \"Delhi\",\n" +
            "        \"customer_pincode\": \"110096\",\n" +
            "        \"customer_country\": \"India\",\n" +
            "        \"pickup_code\": \"247001\",\n" +
            "        \"pickup_location\": \"Old chilkana road near shiv mandir, , Saharanpur, Uttar Pradesh, 247001\",\n" +
            "        \"pickup_location_id\": 6828464,\n" +
            "        \"pickup_id\": \"SRPID-45960419\",\n" +
            "        \"ship_type\": 1,\n" +
            "        \"courier_mode\": 0,\n" +
            "        \"currency\": \"INR\",\n" +
            "        \"country_code\": 99,\n" +
            "        \"exchange_rate_usd\": 0,\n" +
            "        \"exchange_rate_inr\": 0,\n" +
            "        \"state_code\": 1483,\n" +
            "        \"payment_status\": \"\",\n" +
            "        \"delivery_code\": \"110096\",\n" +
            "        \"total\": 1500,\n" +
            "        \"total_inr\": 0,\n" +
            "        \"total_usd\": 0,\n" +
            "        \"net_total\": \"1500.00\",\n" +
            "        \"other_charges\": \"0.00\",\n" +
            "        \"other_discounts\": \"0.00\",\n" +
            "        \"giftwrap_charges\": \"0.00\",\n" +
            "        \"expedited\": 0,\n" +
            "        \"sla\": \"2 days\",\n" +
            "        \"cod\": 0,\n" +
            "        \"tax\": 0,\n" +
            "        \"total_kerala_cess\": \"\",\n" +
            "        \"discount\": 0,\n" +
            "        \"status\": \"PICKUP SCHEDULED\",\n" +
            "        \"sub_status\": null,\n" +
            "        \"status_code\": 4,\n" +
            "        \"master_status\": \"\",\n" +
            "        \"payment_method\": \"prepaid\",\n" +
            "        \"cod_recieved_hover_text\": \"\",\n" +
            "        \"purpose_of_shipment\": 0,\n" +
            "        \"channel_created_at\": \"15 Mar 2026 03:03 AM\",\n" +
            "        \"created_at\": \"15 Mar 2026 03:03 AM\",\n" +
            "        \"order_date\": \"15 Mar 2026\",\n" +
            "        \"updated_at\": \"15 Mar 2026 03:03 AM\",\n" +
            "        \"products\": [\n" +
            "            {\n" +
            "                \"id\": 1838901752,\n" +
            "                \"order_id\": 1235119992,\n" +
            "                \"product_id\": 257819918,\n" +
            "                \"name\": \"T-shirt Color White Docker\",\n" +
            "                \"sku\": \"Tshirt-1230\",\n" +
            "                \"description\": null,\n" +
            "                \"channel_order_product_id\": \"1838901752\",\n" +
            "                \"channel_sku\": \"Tshirt-1230\",\n" +
            "                \"hsn\": \"123456\",\n" +
            "                \"model\": null,\n" +
            "                \"manufacturer\": null,\n" +
            "                \"brand\": null,\n" +
            "                \"color\": null,\n" +
            "                \"size\": null,\n" +
            "                \"custom_field\": \"\",\n" +
            "                \"custom_field_value\": \"\",\n" +
            "                \"custom_field_value_string\": \"\",\n" +
            "                \"weight\": 0,\n" +
            "                \"dimensions\": \"0x0x0\",\n" +
            "                \"price\": 1500,\n" +
            "                \"cost\": 1500,\n" +
            "                \"mrp\": 0,\n" +
            "                \"quantity\": 1,\n" +
            "                \"returnable_quantity\": 0,\n" +
            "                \"tax\": 0,\n" +
            "                \"status\": 1,\n" +
            "                \"net_total\": 1500,\n" +
            "                \"discount\": 0,\n" +
            "                \"product_options\": [],\n" +
            "                \"selling_price\": 1500,\n" +
            "                \"tax_percentage\": 0,\n" +
            "                \"discount_including_tax\": 0,\n" +
            "                \"channel_category\": \"\",\n" +
            "                \"packaging_material\": \"\",\n" +
            "                \"additional_material\": \"\",\n" +
            "                \"is_free_product\": \"\",\n" +
            "                \"package\": \"\",\n" +
            "                \"package_height\": \"\",\n" +
            "                \"package_length\": \"\",\n" +
            "                \"package_weight\": \"\",\n" +
            "                \"package_width\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"invoice_no\": \"Retail00048\",\n" +
            "        \"invoice_date\": \"2026-03-15 03:03:09\",\n" +
            "        \"shipments\": {\n" +
            "            \"id\": 1231424952,\n" +
            "            \"order_id\": 1235119992,\n" +
            "            \"channel_id\": 5917595,\n" +
            "            \"code\": \"\",\n" +
            "            \"cost\": \"0.00\",\n" +
            "            \"tax\": \"0.00\",\n" +
            "            \"awb\": \"367905227346\",\n" +
            "            \"last_mile_awb\": null,\n" +
            "            \"rto_awb\": \"\",\n" +
            "            \"awb_assign_date\": \"2026-03-15 03:03:11\",\n" +
            "            \"etd\": \"19 Mar 2026\",\n" +
            "            \"delivered_date\": \"\",\n" +
            "            \"quantity\": 1,\n" +
            "            \"cod_charges\": \"0.00\",\n" +
            "            \"weight\": 0.25,\n" +
            "            \"volumetric_weight\": 0.675,\n" +
            "            \"dimensions\": \"15.000x15.000x15.000\",\n" +
            "            \"comment\": \"\",\n" +
            "            \"courier\": \"Amazon Shipping Surface 1kg\",\n" +
            "            \"courier_id\": 29,\n" +
            "            \"manifest_id\": \"\",\n" +
            "            \"manifest_escalate\": false,\n" +
            "            \"status\": \"PICKUP GENERATED\",\n" +
            "            \"isd_code\": \"+91\",\n" +
            "            \"created_at\": \"15th Mar 2026 03:03 AM\",\n" +
            "            \"updated_at\": \"15th Mar 2026 03:03 AM\",\n" +
            "            \"pod\": null,\n" +
            "            \"eway_bill_number\": \"-\",\n" +
            "            \"eway_bill_date\": null,\n" +
            "            \"length\": 15,\n" +
            "            \"breadth\": 15,\n" +
            "            \"height\": 15,\n" +
            "            \"rto_initiated_date\": \"\",\n" +
            "            \"rto_delivered_date\": \"\",\n" +
            "            \"shipped_date\": \"\",\n" +
            "            \"package_images\": \"\",\n" +
            "            \"is_rto\": false,\n" +
            "            \"eway_required\": false,\n" +
            "            \"invoice_link\": \"https://shiprocket-db-mum.s3.ap-south-1.amazonaws.com/5369436/invoices/Retail00048.pdf\",\n" +
            "            \"is_darkstore_courier\": 0,\n" +
            "            \"courier_custom_rule\": \"\",\n" +
            "            \"is_single_shipment\": true,\n" +
            "            \"pickup_scheduled_date\": \"15-Mar-2026\",\n" +
            "            \"order_product_id\": null,\n" +
            "            \"number\": null,\n" +
            "            \"name\": null,\n" +
            "            \"order_item_id\": null\n" +
            "        },\n" +
            "        \"awb_data\": {\n" +
            "            \"awb\": \"367905227346\",\n" +
            "            \"applied_weight\": 0.675,\n" +
            "            \"charged_weight\": \"\",\n" +
            "            \"charged_dimension\": null,\n" +
            "            \"billed_weight\": \"\",\n" +
            "            \"routing_code\": \"\",\n" +
            "            \"rto_routing_code\": \"\",\n" +
            "            \"charges\": {\n" +
            "                \"zone\": \"z_d\",\n" +
            "                \"cod_charges\": \"\",\n" +
            "                \"applied_weight_amount\": \"115.00\",\n" +
            "                \"freight_charges\": \"115.00\",\n" +
            "                \"applied_weight\": \"0.675\",\n" +
            "                \"charged_weight\": \"\",\n" +
            "                \"charged_weight_amount\": \"\",\n" +
            "                \"charged_weight_amount_rto\": \"0.00\",\n" +
            "                \"applied_weight_amount_rto\": \"0.00\",\n" +
            "                \"billing_amount\": \"\",\n" +
            "                \"service_type_id\": \"\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"order_insurance\": {\n" +
            "            \"insurance_status\": \"No\",\n" +
            "            \"policy_no\": \"N/A\",\n" +
            "            \"claim_enable\": false\n" +
            "        },\n" +
            "        \"return_pickup_data\": \"\",\n" +
            "        \"company_logo\": \"https://s3-ap-south-1.amazonaws.com/shiprocket-db-mum/5369436/logos/\",\n" +
            "        \"allow_return\": 0,\n" +
            "        \"is_return\": 0,\n" +
            "        \"is_incomplete\": 0,\n" +
            "        \"errors\": [],\n" +
            "        \"payment_code\": null,\n" +
            "        \"billing_city\": \"\",\n" +
            "        \"billing_name\": \"\",\n" +
            "        \"billing_email\": \"\",\n" +
            "        \"billing_phone\": \"\",\n" +
            "        \"billing_alternate_phone\": \"\",\n" +
            "        \"billing_state_name\": \"\",\n" +
            "        \"billing_address\": \"\",\n" +
            "        \"billing_country_name\": \"\",\n" +
            "        \"billing_pincode\": \"\",\n" +
            "        \"billing_address_2\": \"\",\n" +
            "        \"billing_mobile_country_code\": \"\",\n" +
            "        \"isd_code\": \"+91\",\n" +
            "        \"billing_state_id\": \"\",\n" +
            "        \"billing_country_id\": \"\",\n" +
            "        \"freight_description\": \"Forward charges\",\n" +
            "        \"reseller_name\": \"\",\n" +
            "        \"shipping_is_billing\": 0,\n" +
            "        \"company_name\": null,\n" +
            "        \"shipping_title\": \"\",\n" +
            "        \"allow_channel_order_sync\": false,\n" +
            "        \"uib-tooltip-text\": \"Re-fetch orders with updated details\",\n" +
            "        \"api_order_id\": null,\n" +
            "        \"allow_multiship\": 0,\n" +
            "        \"other_sub_orders\": [],\n" +
            "        \"others\": {\n" +
            "            \"billing_alternate_phone\": \"xxxxxxxxxx\",\n" +
            "            \"billing_isd_code\": \"+91\",\n" +
            "            \"buyer_psid\": null,\n" +
            "            \"cargox_deminimis\": 0,\n" +
            "            \"client_id\": null,\n" +
            "            \"company_name\": null,\n" +
            "            \"container_type\": \"\",\n" +
            "            \"currency_code\": null,\n" +
            "            \"custom_clearance_mode\": \"\",\n" +
            "            \"customer_gstin\": null,\n" +
            "            \"customer_isd_code\": \"+91\",\n" +
            "            \"customer_phone_number\": \"\",\n" +
            "            \"dimensions\": \"15x15x15\",\n" +
            "            \"eway_bill_number\": \"\",\n" +
            "            \"fba_address_id\": \"0\",\n" +
            "            \"fba_order\": \"\",\n" +
            "            \"international_ocean\": 0,\n" +
            "            \"international_order_type\": \"\",\n" +
            "            \"invoice_amount\": null,\n" +
            "            \"invoice_billig_name\": \"\",\n" +
            "            \"invoice_date\": null,\n" +
            "            \"invoice_number\": null,\n" +
            "            \"is_b2b_international\": \"\",\n" +
            "            \"is_heavy\": 0,\n" +
            "            \"is_order_verified\": 0,\n" +
            "            \"is_palletisation\": 0,\n" +
            "            \"is_self_drop\": 0,\n" +
            "            \"mps_data\": \"\",\n" +
            "            \"order_verified_at\": \"\",\n" +
            "            \"package_count\": 1,\n" +
            "            \"packaging_unit_details\": null,\n" +
            "            \"quantity\": 1,\n" +
            "            \"reseller_name\": null,\n" +
            "            \"rto_address_id\": null,\n" +
            "            \"ship_date\": null,\n" +
            "            \"shipping_charges\": 0,\n" +
            "            \"shipping_is_billing\": true,\n" +
            "            \"shipping_service_type\": \"\",\n" +
            "            \"source_name\": \"external\",\n" +
            "            \"transaction_date_time\": \"\",\n" +
            "            \"transaction_id\": \"\",\n" +
            "            \"vendor_code\": null,\n" +
            "            \"vendor_id\": null,\n" +
            "            \"weight\": 0.25\n" +
            "        },\n" +
            "        \"international_b2b\": \"\",\n" +
            "        \"fba_order\": \"\",\n" +
            "        \"custom_clearance_mode\": \"\",\n" +
            "        \"is_order_verified\": 2,\n" +
            "        \"extra_info\": {\n" +
            "            \"address_category\": \"unable_to_predict\",\n" +
            "            \"address_risk\": \"NA\",\n" +
            "            \"address_score\": \"0\",\n" +
            "            \"applied_sr_challan\": false,\n" +
            "            \"buyer_dnd_status\": \"0\",\n" +
            "            \"insurace_opted_at_order_creation\": false,\n" +
            "            \"insurance_config_snapshot\": {\n" +
            "                \"config_overwrite_by_seller\": 0,\n" +
            "                \"courier_config\": {\n" +
            "                    \"courier_id\": 29,\n" +
            "                    \"courier_name\": \"Amazon Shipping Surface 1kg\",\n" +
            "                    \"maximum_insurance_liability\": 475000,\n" +
            "                    \"secure_shipment_disabled\": 0\n" +
            "                },\n" +
            "                \"final_maximum_insurance_liability\": 475000,\n" +
            "                \"final_minimum_insurance_threshold\": 2500,\n" +
            "                \"seller_config\": {\n" +
            "                    \"disable_insurance_dynamic_pricing\": 0,\n" +
            "                    \"insurance_config_set\": 0,\n" +
            "                    \"maximum_insurance_liability\": 475000,\n" +
            "                    \"minimum_insurance_threshold\": 2500,\n" +
            "                    \"return_claim_percent\": 50,\n" +
            "                    \"rto_claim_percent\": 40,\n" +
            "                    \"secure_shipment_disabled\": 0,\n" +
            "                    \"secure_shipment_disabled_ip\": 0\n" +
            "                },\n" +
            "                \"tier_config\": {\n" +
            "                    \"maximum_insurance_liability\": 475000,\n" +
            "                    \"minimum_insurance_threshold\": 2500\n" +
            "                }\n" +
            "            },\n" +
            "            \"is_document\": 0,\n" +
            "            \"is_promise_assured\": 0,\n" +
            "            \"order_metadata\": {\n" +
            "                \"client_ip\": \"106.219.71.38\",\n" +
            "                \"device\": false,\n" +
            "                \"is_insurance_opt\": false,\n" +
            "                \"platform\": \"NONE\",\n" +
            "                \"request_type\": \"external\",\n" +
            "                \"type\": \"create\"\n" +
            "            },\n" +
            "            \"order_risk\": \"low\",\n" +
            "            \"order_risk_tags\": \"[]\",\n" +
            "            \"order_type\": \"\",\n" +
            "            \"other_courier_dg_status\": true,\n" +
            "            \"rto_pred_enable\": \"0\",\n" +
            "            \"rto_reason\": \"Prepaid orders have low RTO risk.\",\n" +
            "            \"rto_reason_code\": \"R006\",\n" +
            "            \"rto_risk\": \"low\",\n" +
            "            \"rto_score\": \"0.1\",\n" +
            "            \"sense_lat\": \"28.618813\",\n" +
            "            \"sense_long\": \"77.328994\"\n" +
            "        },\n" +
            "        \"package_list\": {\n" +
            "            \"packages\": \"\",\n" +
            "            \"selected\": false,\n" +
            "            \"select_item\": false,\n" +
            "            \"all_packages\": []\n" +
            "        },\n" +
            "        \"dup\": 0,\n" +
            "        \"is_blackbox_seller\": false,\n" +
            "        \"shipping_method\": \"SR\",\n" +
            "        \"refund_detail\": [],\n" +
            "        \"pickup_address\": {\n" +
            "            \"id\": 6828464,\n" +
            "            \"pin_code\": \"247001\",\n" +
            "            \"pickup_code\": \"work\",\n" +
            "            \"name\": \"Aman Saini\",\n" +
            "            \"phone\": \"9319118772\",\n" +
            "            \"phone_verified\": 1,\n" +
            "            \"address\": \"Old chilkana road near shiv mandir\",\n" +
            "            \"address_2\": \"\",\n" +
            "            \"city\": \"Saharanpur\",\n" +
            "            \"state\": \"Uttar Pradesh\",\n" +
            "            \"country\": \"India\",\n" +
            "            \"email\": \"amansaini1407@gmail.com\",\n" +
            "            \"lat\": \"0\",\n" +
            "            \"long\": \"0\",\n" +
            "            \"new\": 1\n" +
            "        },\n" +
            "        \"eway_bill_number\": \"\",\n" +
            "        \"eway_bill_url\": \"\",\n" +
            "        \"eway_required\": false,\n" +
            "        \"irn_no\": \"\",\n" +
            "        \"engage\": null,\n" +
            "        \"seller_can_edit\": false,\n" +
            "        \"seller_can_cancell\": false,\n" +
            "        \"is_post_ship_status\": false,\n" +
            "        \"order_tag\": \"\",\n" +
            "        \"international_order_tag\": \"\",\n" +
            "        \"qc_status\": \"\",\n" +
            "        \"on_hold_reason\": \"\",\n" +
            "        \"on_hold_documents\": [],\n" +
            "        \"qc_reason\": null,\n" +
            "        \"qc_image\": \"\",\n" +
            "        \"product_qc\": [],\n" +
            "        \"courier_qc_image\": [],\n" +
            "        \"seller_request\": null,\n" +
            "        \"change_payment_mode\": true,\n" +
            "        \"etd_date\": \"19-03-2026 23:59:00\",\n" +
            "        \"out_for_delivery_date\": null,\n" +
            "        \"delivered_date\": null,\n" +
            "        \"remittance_date\": \"\",\n" +
            "        \"remittance_utr\": \"\",\n" +
            "        \"remittance_status\": \"\",\n" +
            "        \"shipping_bill\": \"\",\n" +
            "        \"quick_pick\": \"\",\n" +
            "        \"shipping_bill_number\": \"\",\n" +
            "        \"comment\": \"comment Data\",\n" +
            "        \"exc_data\": [],\n" +
            "        \"exchange_awb\": null,\n" +
            "        \"exchange_status\": null,\n" +
            "        \"rto_prediction\": \"\",\n" +
            "        \"marketplace_id\": \"\",\n" +
            "        \"marketplace_name\": \"\",\n" +
            "        \"can_edit_dimension\": true,\n" +
            "        \"partial_cod_value\": null,\n" +
            "        \"partial_cod_collected\": null,\n" +
            "        \"insurance_excluded\": false,\n" +
            "        \"cluster_code\": \"\",\n" +
            "        \"transaction_detail\": {\n" +
            "            \"transaction_id\": null,\n" +
            "            \"transaction_date_time\": null\n" +
            "        },\n" +
            "        \"fulfillment_tat\": \"\",\n" +
            "        \"wms_fulfillment_tat_datetime\": null,\n" +
            "        \"fulfillment_status\": \"\",\n" +
            "        \"qc_bad_images\": \"\",\n" +
            "        \"putaway_return_video\": \"\",\n" +
            "        \"fulfillment_details\": {\n" +
            "            \"srf_packaging_material\": \"\",\n" +
            "            \"srf_additional_material\": \"\"\n" +
            "        },\n" +
            "        \"sender\": {},\n" +
            "        \"commission\": null,\n" +
            "        \"receiver\": {},\n" +
            "        \"coupon_is_visible\": false,\n" +
            "        \"coupons\": \"\"\n" +
            "    }\n" +
            "}";




//    public static void main(String ar[])
//    {
//        JsonObject specificDetails = JsonParser.parseString(specificOrderDetails).getAsJsonObject();
//        JsonObject orderDetailsData = specificDetails.getAsJsonObject("data");
//        JsonObject shipment = orderDetailsData.getAsJsonObject("shipments");
//
//        String pickup_scheduled_date = shipment.get("pickup_scheduled_date").getAsString();
//        String pickup_status = shipment.get("status").getAsString();
//        String pickup_courier_name = shipment.get("courier").getAsString();
//        String pickup_etd = shipment.get("etd").getAsString();
//
//        System.out.println("Pickup Scheduled Date : " + pickup_scheduled_date);
//        System.out.println("Pickup Status : " + pickup_status);
//        System.out.println("Courier Name : " + pickup_courier_name);
//        System.out.println("ETD : " + pickup_etd);
//
//    }

}
