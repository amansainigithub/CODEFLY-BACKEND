package com.coder.springjwt.services.sellerServices.ordersActionService.imple;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.CheckServiceAvailability;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.CreateOrderDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder.OrderItemDtoShipRocket;
import com.coder.springjwt.buckets.shiprocketBucket.shiprocketServices.imple.ShipRocketServiceImple;
import com.coder.springjwt.dtos.sellerPayloads.orders.OrderAcceptDto;
import com.coder.springjwt.emuns.seller.OrderStatus;
import com.coder.springjwt.globalExceptionHandler.OrderIdNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.customerModels.orders.OrderItems;
import com.coder.springjwt.repository.customerRepository.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.sellerServices.ordersActionService.OrdersActionService;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class OrdersActionServiceImple implements OrdersActionService {


    @Autowired
    private UserHelper userHelper;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ShipRocketServiceImple  shipRocketServiceImple;

    @Override
    public ResponseEntity<?> orderAccept(OrderAcceptDto orderAcceptDto) {
        try {

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUserId= userData.get("userId").trim();
            String sellerUsername= userData.get("username").trim();

            if(!userData.get("username").trim().equals(orderAcceptDto.getUsername().trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            log.info("orderAcceptDto :: {} " + orderAcceptDto);
            if(orderAcceptDto.getId() == null || orderAcceptDto.getOrderNoPerItem() == null)
            {
                throw new RuntimeException("Id or Order Item Id not Found | please check...");
            }


            OrderItems orderItems = this.orderItemsRepository.findById(orderAcceptDto.getId())
                    .orElseThrow(() -> new OrderIdNotFoundException("Order Id Not Found " + orderAcceptDto.getId()));

            log.info("==========Data Received==========");
            log.info("OrderId :: " + orderItems.getId());
            log.info("Product Name :: " + orderItems.getProductName());


            //CREATE ORDER TO SHIP-ROCKET
            System.out.println("==============CREATE ORDER TO SHIP-ROCKET================");
            Map<String, Long> shipRocketData = readyOrderToShipRocket(orderItems);
            Long shipmentId = shipRocketData.get("SHIPMENT_ID");
            Long orderId = shipRocketData.get("ORDER_ID");
            log.info("ORDER CREATED SUCCESS IN SHIP-ROCKET PORTAL");

            System.out.println("SHIPMENT_ID::: " + shipRocketData.get("SHIPMENT_ID"));
            System.out.println("ORDER_ID::: " + shipRocketData.get("ORDER_ID"));

            CheckServiceAvailability csa = new CheckServiceAvailability();
            csa.setShipment_id(String.valueOf(shipmentId));
            csa.setOrder_id(String.valueOf(orderId));
            csa.setCod("0");

            Map<String, String> shipRocketResponse = shipRocketServiceImple.checkCourierAvailabilityShipRocketFunction(csa);
            String courierName = shipRocketResponse.get("COURIER_NAME");
            String courierId = shipRocketResponse.get("COURIER_ID");
            String etd = shipRocketResponse.get("ETD");
            String rate = shipRocketResponse.get("RATE");
            String awb = shipRocketResponse.get("AWB");
            String deliveryDays = shipRocketResponse.get("DELIVERY_DAYS");
            String pickupStatus = shipRocketResponse.get("PICKUP_STATUS");
            String pickupDate = shipRocketResponse.get("PICKUP_DATE");
            String pickupToken = shipRocketResponse.get("PICKUP_TOKEN");
            String pickupData = shipRocketResponse.get("PICKUP_DATA");

            System.out.println("==============ORDER ACTION STARTING================");
            System.out.println("Courier Name : " + courierName);
            System.out.println("Courier ID : " + courierId);
            System.out.println("ETD : " + etd);
            System.out.println("Rate : " + rate);
            System.out.println("AWB : " + awb);
            System.out.println("Delivery Days : " + deliveryDays);
            System.out.println("pickup Status : " + pickupStatus);
            System.out.println("pickup Date : " + pickupDate);
            System.out.println("pickup Token : " + pickupToken);
            System.out.println("pickup Data : " + pickupData);

            System.out.println("CHECK AVAILABILITY SUCCESS || AWB ASSIGN SUCCESS || PICKUP SUCCESS || ");
            orderItems.setShipRocketCourierId(String.valueOf(courierId));
            orderItems.setShipRocketCourierName(courierName);
            orderItems.setShipRocketEtd(etd);
            orderItems.setShipRocketCourierPrice(Double.parseDouble(rate));
            orderItems.setShipRocketAwbCode(awb);
            orderItems.setShipRocketDeliveryDays(deliveryDays);
            orderItems.setShipRocketPickupStatus(pickupStatus);
            orderItems.setShipRocketPickupDate(pickupDate);
            orderItems.setShipRocketPickupToken(pickupToken);

            orderItemsRepository.save(orderItems);
            System.out.println("DATA UPDATE SUCCESS");
            System.out.println("==============ORDER ACTION ENDING....================");

            return ResponseGenerator.generateSuccessResponse("SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }




    public Map<String,Long> readyOrderToShipRocket(OrderItems orderItems )
    {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = now.format(formatter);

            Random random = new Random();
            int number = 100000 + random.nextInt(900000);
            log.info("6 Digit Number: " + number);

            CreateOrderDtoShipRocket createOrderDtoShipRocket = new CreateOrderDtoShipRocket();
            createOrderDtoShipRocket.setOrder_id(String.valueOf(number));
            createOrderDtoShipRocket.setOrder_date(formattedDate);
            createOrderDtoShipRocket.setPickup_location("work");
            createOrderDtoShipRocket.setComment("comment Data");
            createOrderDtoShipRocket.setBilling_customer_name("Aman");
            createOrderDtoShipRocket.setBilling_last_name("Suryavanshi");
            createOrderDtoShipRocket.setBilling_address("Samrat Vikram Colony near Petrol 248001 Delhi");
            createOrderDtoShipRocket.setBilling_address_2("near HP Petrol");
            createOrderDtoShipRocket.setBilling_city("Delhi");
            createOrderDtoShipRocket.setBilling_pincode(110096);
            createOrderDtoShipRocket.setBilling_state("delhi");
            createOrderDtoShipRocket.setBilling_country("India");
            createOrderDtoShipRocket.setBilling_email("test@gmail.com");
            createOrderDtoShipRocket.setBilling_phone(9878458510l);
            createOrderDtoShipRocket.setShipping_is_billing(true);
            createOrderDtoShipRocket.setShipping_customer_name("");
            createOrderDtoShipRocket.setShipping_last_name("");
            createOrderDtoShipRocket.setShipping_address("");
            createOrderDtoShipRocket.setShipping_address_2("");
            createOrderDtoShipRocket.setShipping_city("");
            createOrderDtoShipRocket.setShipping_country("");
            createOrderDtoShipRocket.setShipping_state("");
            createOrderDtoShipRocket.setShipping_email("");
            createOrderDtoShipRocket.setShipping_phone("");
            createOrderDtoShipRocket.setPayment_method("prepaid");
            createOrderDtoShipRocket.setShipping_charges(0);
            createOrderDtoShipRocket.setGiftwrap_charges(0);
            createOrderDtoShipRocket.setTransaction_charges(0);
            createOrderDtoShipRocket.setTotal_discount(0);
            createOrderDtoShipRocket.setSub_total(1500);
            createOrderDtoShipRocket.setLength(15);
            createOrderDtoShipRocket.setBreadth(15);
            createOrderDtoShipRocket.setHeight(15);
            createOrderDtoShipRocket.setWeight(0.25);

            OrderItemDtoShipRocket orderItemDtoShipRocket =new OrderItemDtoShipRocket();
            orderItemDtoShipRocket.setName("T-shirt Color White Docker");
            orderItemDtoShipRocket.setSku("Tshirt-1230");
            orderItemDtoShipRocket.setUnits(1);
            orderItemDtoShipRocket.setSelling_price(1500);
            orderItemDtoShipRocket.setDiscount("");
            orderItemDtoShipRocket.setTax("");
            orderItemDtoShipRocket.setHsn(123456);

            ArrayList<OrderItemDtoShipRocket> orderItemList = new ArrayList<>();
            orderItemList.add(orderItemDtoShipRocket);

            createOrderDtoShipRocket.setOrder_items(orderItemList);


            JsonNode jsonNodeData = shipRocketServiceImple.createOrderShipRocketAndValidToken(createOrderDtoShipRocket);


            //Data Extract with ShipRocket JsonNodeData
            long shipRocketOrderId = jsonNodeData.path("order_id").asLong();
            String shipRocketChannelOrderId = jsonNodeData.path("channel_order_id").asText();
            long shipRocketShipmentId = jsonNodeData.path("shipment_id").asLong();
            String shipRocketStatus = jsonNodeData.path("status").asText();
            String shipRocketAwbCode = jsonNodeData.path("awb_code").asText();
            String shipRocketCourierCompanyId = jsonNodeData.path("courier_company_id").asText();
            String shipRocketCourierName = jsonNodeData.path("courier_name").asText();

            orderItems.setShipRocketOrderId(String.valueOf(shipRocketOrderId));
            orderItems.setShipRocketChannelOrderId(shipRocketChannelOrderId);
            orderItems.setShipRocketShipmentId(String.valueOf(shipRocketShipmentId));
            orderItems.setShipRocketStatus(shipRocketStatus);
            orderItems.setShipRocketAwbCode(shipRocketAwbCode);
            orderItems.setShipRocketCourierId(shipRocketCourierCompanyId);
            orderItems.setShipRocketCourierName(shipRocketCourierName);
            log.info("Data Extract with ShipRocket JsonNodeData");

            //Change ShipRocket Status
            orderItems.setOrderStatus(OrderStatus.CONFIRMED.toString());
            log.info("Order Status Confirmed Success");

            //save Order Items with shipRocket Status
            this.orderItemsRepository.save(orderItems);
            log.info(" ======= Order Item Update Success ========= ");

            return Map.of("SHIPMENT_ID" , shipRocketShipmentId,"ORDER_ID",shipRocketOrderId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
