package com.coder.springjwt.services.sellerServices.ordersService.imple;

import com.coder.springjwt.dtos.sellerPayloads.orders.OrderRequestDto;
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
    public ResponseEntity<?> getActiveOrders(Integer page, Integer size, String username) {
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
                   .findBySellerIdAndSellerUsername(sellerUserId, sellerUsername ,PageRequest.of(page, size,
                           Sort.by("id").descending()));

           List<OrderRequestDto> orderRequestDtos = new ArrayList<>();

           for (OrderItems entity : orderItems.getContent()) {
               orderRequestDtos.add(convertToDTO(entity));
           }

           PageImpl<OrderRequestDto> ordersPageImple = new PageImpl<>(orderRequestDtos,
                                                    orderItems.getPageable(), orderItems.getTotalElements());

           return ResponseGenerator.generateSuccessResponse(ordersPageImple,"SUCCESS");
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse();
       }
    }


    private OrderRequestDto convertToDTO(OrderItems entity) {

        OrderRequestDto ord = new OrderRequestDto();

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

        return ord;
    }

}
