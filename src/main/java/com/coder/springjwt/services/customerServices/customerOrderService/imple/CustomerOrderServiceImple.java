package com.coder.springjwt.services.customerServices.customerOrderService.imple;

import com.coder.springjwt.dtos.customerPayloads.orderItemsDto.OrderItemsDto;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerModels.orders.OrderItems;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerRepository.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.customerServices.customerOrderService.CustomerOrderService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomerOrderServiceImple implements CustomerOrderService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Override
    public ResponseEntity<?> fetchCustomerOrders(Integer page, Integer size , String customerName) {
        try {
            //USER DATA
            Map<String, String> currentUser = userHelper.getCurrentUser();
            String username = currentUser.get("username");
            User user = this.userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User Not Fount"));

            if(customerName.equals(user.getUsername()))
            {
                Page<OrderItems> itemsPage = this.orderItemsRepository.
                                                findByUserIdAndUsername(
                                                 String.valueOf(user.getId()),
                                                 user.getUsername() ,
                                                 PageRequest.of(page , size, Sort.by("id").descending()));

                List<OrderItemsDto> dtoList = new ArrayList<>();

                for (OrderItems item : itemsPage.getContent()) {
                    OrderItemsDto dto = this.convertToDto(item);
                    dtoList.add(dto);
                }

                PageImpl<OrderItemsDto> orderItemsData = new PageImpl<>(dtoList, itemsPage.getPageable(), itemsPage.getTotalElements());
                return ResponseGenerator.generateSuccessResponse(orderItemsData , "SUCCESS");
            }
            else{
                throw new RuntimeException("Invalid Username !! Error");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse();
        }
    }


    private OrderItemsDto convertToDto(OrderItems order) {

        OrderItemsDto dto = new OrderItemsDto();

        dto.setFileUrl(order.getFileUrl());
        dto.setId(order.getId());
        dto.setProductId(order.getProductId());
        dto.setProductName(order.getProductName());
        dto.setProductPrice(order.getProductPrice());
        dto.setProductSize(order.getProductSize());
        dto.setQuantity(order.getQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setProductColor(order.getProductColor());
        dto.setProductMrp(order.getProductMrp());
        dto.setProductDiscount(order.getProductDiscount());
        dto.setOrderNoPerItem(order.getOrderNoPerItem());
        dto.setProductBrand(order.getProductBrand());

        // shipping address
        dto.setShippingAddressId(order.getOrderShippingAddresses().getId());
        dto.setCountry(order.getOrderShippingAddresses().getCountry());
        dto.setCustomerName(order.getOrderShippingAddresses().getCustomerName());
        dto.setMobileNumber(order.getOrderShippingAddresses().getMobileNumber());
        dto.setArea(order.getOrderShippingAddresses().getArea());
        dto.setPostalCode(order.getOrderShippingAddresses().getPostalCode());
        dto.setAddressLine1(order.getOrderShippingAddresses().getAddressLine1());
        dto.setAddressLine2(order.getOrderShippingAddresses().getAddressLine2());
        dto.setDefaultAddress(order.getOrderShippingAddresses().isDefaultAddress());
        dto.setCity(order.getOrderShippingAddresses().getCity());
         dto.setState(order.getOrderShippingAddresses().getState());

        return dto;
    }


}
