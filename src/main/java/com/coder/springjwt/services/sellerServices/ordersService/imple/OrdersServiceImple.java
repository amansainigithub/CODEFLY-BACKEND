package com.coder.springjwt.services.sellerServices.ordersService.imple;

import com.coder.springjwt.dtos.sellerPayloads.orders.*;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.customerModels.orders.OrderItems;
import com.coder.springjwt.repository.customerRepository.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.sellerServices.ordersService.OrdersService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrdersServiceImple implements OrdersService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private OrderItemsRepository orderItemsRepository;


    @Override
    public ResponseEntity<?> getPendingOrders(Integer page, Integer size, String username) {
       try {
           log.info(OrdersServiceImple.class.getName() + " working....");

           // VALIDATE CURRENT USER---
           Map<String, String> userData = userHelper.getCurrentUser();
           String sellerUserId= userData.get("userId").trim();
           String sellerUsername= userData.get("username").trim();

           if(!userData.get("username").trim().equals(username.trim()))
           {
               throw new UsernameNotFoundException("Username not found Exception...");
           }


           Page<OrderItems> orderItems = this.orderItemsRepository
                   .findBySellerIdAndSellerUsernameAndOrderStatus(sellerUserId, sellerUsername ,
                           "PENDING", PageRequest.of(page, size,
                           Sort.by("id").descending()));

           List<PendingOrdersDto> pendingOrdersDtos = new ArrayList<>();

           for (OrderItems entity : orderItems.getContent()) {
               pendingOrdersDtos.add(convertToDTO(entity));
           }

           PageImpl<PendingOrdersDto> ordersPageImple = new PageImpl<>(pendingOrdersDtos,
                                                    orderItems.getPageable(), orderItems.getTotalElements());

           return ResponseGenerator.generateSuccessResponse(ordersPageImple,"SUCCESS");
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse();
       }
    }




    private PendingOrdersDto convertToDTO(OrderItems entity) {

        PendingOrdersDto ord = new PendingOrdersDto();

        ord.setId(entity.getId());
        ord.setProductId(entity.getProductId());
        ord.setProductName(entity.getProductName());
        ord.setProductPrice(entity.getProductPrice());
        ord.setProductBrand(entity.getProductBrand());
        ord.setProductSize(entity.getProductSize());
        ord.setQuantity(entity.getQuantity());
        ord.setTotalPrice(entity.getTotalPrice());
        ord.setFileUrl(entity.getFileUrl());
        ord.setProductColor(entity.getProductColor());
        ord.setProductMrp(entity.getProductMrp());
        ord.setProductDiscount(entity.getProductDiscount());
        ord.setOrderId(entity.getOrderId());
        ord.setPaymentState(entity.getPaymentState());
        ord.setUserId(entity.getUserId());
        ord.setUsername(entity.getUsername());
        ord.setPaymentMode(entity.getPaymentMode());
        ord.setOrderReferenceNo(entity.getOrderReferenceNo());
        ord.setOrderNoPerItem(entity.getOrderNoPerItem());
        ord.setSellerId(entity.getSellerId());
        ord.setSellerUsername(entity.getSellerUsername());
        ord.setAddressId(entity.getAddressId());
        ord.setCountry(entity.getCountry());
        ord.setCustomerName(entity.getCustomerName());
        ord.setMobileNumber(entity.getMobileNumber());
        ord.setArea(entity.getArea());
        ord.setPostalCode(entity.getPostalCode());
        ord.setAddressLine1(entity.getAddressLine1());
        ord.setAddressLine2(entity.getAddressLine2());
        ord.setCity(entity.getCity());
        ord.setState(entity.getState());
        ord.setOrderStatus(entity.getOrderStatus());
        ord.setOrderDate(entity.getOrderDate());
        ord.setOrderTime(entity.getOrderTime());

        return ord;
    }











    @Override
    public ResponseEntity<?> getConfirmedOrders(Integer page, Integer size, String username) {
        try {
            log.info(OrdersServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUserId= userData.get("userId").trim();
            String sellerUsername= userData.get("username").trim();

            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }


            Page<OrderItems> orderItems = this.orderItemsRepository
                    .findBySellerIdAndSellerUsernameAndOrderStatus(sellerUserId, sellerUsername ,
                            "CONFIRMED", PageRequest.of(page, size,
                            Sort.by("id").descending()));

            List<ConfirmedOrderDto> confirmedOrderDto = new ArrayList<>();

            for (OrderItems entity : orderItems.getContent()) {
                confirmedOrderDto.add(confirmedOrderToDTO(entity));
            }

            PageImpl<ConfirmedOrderDto> ordersPageImple = new PageImpl<>(confirmedOrderDto,
                    orderItems.getPageable(), orderItems.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(ordersPageImple,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }



    private ConfirmedOrderDto confirmedOrderToDTO(OrderItems entity) {

        ConfirmedOrderDto ord = new ConfirmedOrderDto();

        ord.setId(entity.getId());
        ord.setProductId(entity.getProductId());
        ord.setProductName(entity.getProductName());
        ord.setProductPrice(entity.getProductPrice());
        ord.setProductBrand(entity.getProductBrand());
        ord.setProductSize(entity.getProductSize());
        ord.setQuantity(entity.getQuantity());
        ord.setTotalPrice(entity.getTotalPrice());
        ord.setFileUrl(entity.getFileUrl());
        ord.setProductColor(entity.getProductColor());
        ord.setProductMrp(entity.getProductMrp());
        ord.setProductDiscount(entity.getProductDiscount());
        ord.setOrderId(entity.getOrderId());
        ord.setPaymentState(entity.getPaymentState());
        ord.setUserId(entity.getUserId());
        ord.setUsername(entity.getUsername());
        ord.setPaymentMode(entity.getPaymentMode());
        ord.setOrderReferenceNo(entity.getOrderReferenceNo());
        ord.setOrderNoPerItem(entity.getOrderNoPerItem());
        ord.setSellerId(entity.getSellerId());
        ord.setSellerUsername(entity.getSellerUsername());
        ord.setAddressId(entity.getAddressId());
        ord.setCountry(entity.getCountry());
        ord.setCustomerName(entity.getCustomerName());
        ord.setMobileNumber(entity.getMobileNumber());
        ord.setArea(entity.getArea());
        ord.setPostalCode(entity.getPostalCode());
        ord.setAddressLine1(entity.getAddressLine1());
        ord.setAddressLine2(entity.getAddressLine2());
        ord.setCity(entity.getCity());
        ord.setState(entity.getState());
        ord.setOrderStatus(entity.getOrderStatus());
        ord.setOrderDate(entity.getOrderDate());
        ord.setOrderTime(entity.getOrderTime());

        return ord;
    }





    @Override
    public ResponseEntity<?> getShippedOrders(Integer page, Integer size, String username) {
        try {
            log.info(OrdersServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUserId= userData.get("userId").trim();
            String sellerUsername= userData.get("username").trim();

            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }


            Page<OrderItems> orderItems = this.orderItemsRepository
                    .findBySellerIdAndSellerUsernameAndOrderStatus(sellerUserId, sellerUsername ,
                            "SHIPPED", PageRequest.of(page, size,
                                    Sort.by("id").descending()));

            List<ShippedOrderDto> shippedOrderDtos = new ArrayList<>();

            for (OrderItems entity : orderItems.getContent()) {
                shippedOrderDtos.add(shippedOrderToDTO(entity));
            }

            PageImpl<ShippedOrderDto> ordersPageImple = new PageImpl<>(shippedOrderDtos,
                    orderItems.getPageable(), orderItems.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(ordersPageImple,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }




    private ShippedOrderDto shippedOrderToDTO(OrderItems entity) {

        ShippedOrderDto ord = new ShippedOrderDto();

        ord.setId(entity.getId());
        ord.setProductId(entity.getProductId());
        ord.setProductName(entity.getProductName());
        ord.setProductPrice(entity.getProductPrice());
        ord.setProductBrand(entity.getProductBrand());
        ord.setProductSize(entity.getProductSize());
        ord.setQuantity(entity.getQuantity());
        ord.setTotalPrice(entity.getTotalPrice());
        ord.setFileUrl(entity.getFileUrl());
        ord.setProductColor(entity.getProductColor());
        ord.setProductMrp(entity.getProductMrp());
        ord.setProductDiscount(entity.getProductDiscount());
        ord.setOrderId(entity.getOrderId());
        ord.setPaymentState(entity.getPaymentState());
        ord.setUserId(entity.getUserId());
        ord.setUsername(entity.getUsername());
        ord.setPaymentMode(entity.getPaymentMode());
        ord.setOrderReferenceNo(entity.getOrderReferenceNo());
        ord.setOrderNoPerItem(entity.getOrderNoPerItem());
        ord.setSellerId(entity.getSellerId());
        ord.setSellerUsername(entity.getSellerUsername());
        ord.setAddressId(entity.getAddressId());
        ord.setCountry(entity.getCountry());
        ord.setCustomerName(entity.getCustomerName());
        ord.setMobileNumber(entity.getMobileNumber());
        ord.setArea(entity.getArea());
        ord.setPostalCode(entity.getPostalCode());
        ord.setAddressLine1(entity.getAddressLine1());
        ord.setAddressLine2(entity.getAddressLine2());
        ord.setCity(entity.getCity());
        ord.setState(entity.getState());
        ord.setOrderStatus(entity.getOrderStatus());
        ord.setOrderDate(entity.getOrderDate());
        ord.setOrderTime(entity.getOrderTime());

        return ord;
    }



    @Override
    public ResponseEntity<?> getDeliveredOrders(Integer page, Integer size, String username) {
        try {
            log.info(OrdersServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUserId= userData.get("userId").trim();
            String sellerUsername= userData.get("username").trim();

            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }


            Page<OrderItems> orderItems = this.orderItemsRepository
                    .findBySellerIdAndSellerUsernameAndOrderStatus(sellerUserId, sellerUsername ,
                            "DELIVERED", PageRequest.of(page, size,
                                    Sort.by("id").descending()));

            List<DeliveredOrderDto> deliveredOrderDtos = new ArrayList<>();

            for (OrderItems entity : orderItems.getContent()) {
                deliveredOrderDtos.add(deliveredOrderDto(entity));
            }

            PageImpl<DeliveredOrderDto> ordersPageImple = new PageImpl<>(deliveredOrderDtos,
                    orderItems.getPageable(), orderItems.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(ordersPageImple,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }




    private DeliveredOrderDto deliveredOrderDto(OrderItems entity) {

        DeliveredOrderDto ord = new DeliveredOrderDto();

        ord.setId(entity.getId());
        ord.setProductId(entity.getProductId());
        ord.setProductName(entity.getProductName());
        ord.setProductPrice(entity.getProductPrice());
        ord.setProductBrand(entity.getProductBrand());
        ord.setProductSize(entity.getProductSize());
        ord.setQuantity(entity.getQuantity());
        ord.setTotalPrice(entity.getTotalPrice());
        ord.setFileUrl(entity.getFileUrl());
        ord.setProductColor(entity.getProductColor());
        ord.setProductMrp(entity.getProductMrp());
        ord.setProductDiscount(entity.getProductDiscount());
        ord.setOrderId(entity.getOrderId());
        ord.setPaymentState(entity.getPaymentState());
        ord.setUserId(entity.getUserId());
        ord.setUsername(entity.getUsername());
        ord.setPaymentMode(entity.getPaymentMode());
        ord.setOrderReferenceNo(entity.getOrderReferenceNo());
        ord.setOrderNoPerItem(entity.getOrderNoPerItem());
        ord.setSellerId(entity.getSellerId());
        ord.setSellerUsername(entity.getSellerUsername());
        ord.setAddressId(entity.getAddressId());
        ord.setCountry(entity.getCountry());
        ord.setCustomerName(entity.getCustomerName());
        ord.setMobileNumber(entity.getMobileNumber());
        ord.setArea(entity.getArea());
        ord.setPostalCode(entity.getPostalCode());
        ord.setAddressLine1(entity.getAddressLine1());
        ord.setAddressLine2(entity.getAddressLine2());
        ord.setCity(entity.getCity());
        ord.setState(entity.getState());
        ord.setOrderStatus(entity.getOrderStatus());
        ord.setOrderDate(entity.getOrderDate());
        ord.setOrderTime(entity.getOrderTime());

        return ord;
    }







    @Override
    public ResponseEntity<?> getCancelledOrders(Integer page, Integer size, String username) {
        try {
            log.info(OrdersServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUserId= userData.get("userId").trim();
            String sellerUsername= userData.get("username").trim();

            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }


            Page<OrderItems> orderItems = this.orderItemsRepository
                    .findBySellerIdAndSellerUsernameAndOrderStatus(sellerUserId, sellerUsername ,
                            "CANCELLED", PageRequest.of(page, size,
                                    Sort.by("id").descending()));

            List<CancelledOrderDto> cancelledOrderDtos = new ArrayList<>();

            for (OrderItems entity : orderItems.getContent()) {
                cancelledOrderDtos.add(cancelledOrderDto(entity));
            }

            PageImpl<CancelledOrderDto> ordersPageImple = new PageImpl<>(cancelledOrderDtos,
                    orderItems.getPageable(), orderItems.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(ordersPageImple,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }


    private CancelledOrderDto cancelledOrderDto(OrderItems entity) {

        CancelledOrderDto ord = new CancelledOrderDto();

        ord.setId(entity.getId());
        ord.setProductId(entity.getProductId());
        ord.setProductName(entity.getProductName());
        ord.setProductPrice(entity.getProductPrice());
        ord.setProductBrand(entity.getProductBrand());
        ord.setProductSize(entity.getProductSize());
        ord.setQuantity(entity.getQuantity());
        ord.setTotalPrice(entity.getTotalPrice());
        ord.setFileUrl(entity.getFileUrl());
        ord.setProductColor(entity.getProductColor());
        ord.setProductMrp(entity.getProductMrp());
        ord.setProductDiscount(entity.getProductDiscount());
        ord.setOrderId(entity.getOrderId());
        ord.setPaymentState(entity.getPaymentState());
        ord.setUserId(entity.getUserId());
        ord.setUsername(entity.getUsername());
        ord.setPaymentMode(entity.getPaymentMode());
        ord.setOrderReferenceNo(entity.getOrderReferenceNo());
        ord.setOrderNoPerItem(entity.getOrderNoPerItem());
        ord.setSellerId(entity.getSellerId());
        ord.setSellerUsername(entity.getSellerUsername());
        ord.setAddressId(entity.getAddressId());
        ord.setCountry(entity.getCountry());
        ord.setCustomerName(entity.getCustomerName());
        ord.setMobileNumber(entity.getMobileNumber());
        ord.setArea(entity.getArea());
        ord.setPostalCode(entity.getPostalCode());
        ord.setAddressLine1(entity.getAddressLine1());
        ord.setAddressLine2(entity.getAddressLine2());
        ord.setCity(entity.getCity());
        ord.setState(entity.getState());
        ord.setOrderStatus(entity.getOrderStatus());
        ord.setOrderDate(entity.getOrderDate());
        ord.setOrderTime(entity.getOrderTime());

        return ord;
    }
}
