package com.coder.springjwt.services.customerServices.orderPaymentService.imple;

import com.coder.springjwt.buckets.razorpayBucket.razorpayServices.RazorpayService;
import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.dtos.customerPayloads.cartItemDtos.CartItemsDto;
import com.coder.springjwt.dtos.customerPayloads.orderPaymentDto.OrderPaymentDto;
import com.coder.springjwt.emuns.customer.PaymentModeStatus;
import com.coder.springjwt.emuns.paymentModes.PaymentMode;
import com.coder.springjwt.emuns.paymentState.PaymentState;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerModels.customerAddressModel.CustomerAddress;
import com.coder.springjwt.models.customerModels.orders.OrderItems;
import com.coder.springjwt.models.customerModels.orders.OrderShippingAddress;
import com.coder.springjwt.models.customerModels.paymentsModels.PaymentOrders;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerAddressRepository.CustomerAddressRepo;
import com.coder.springjwt.repository.customerRepository.paymentOrderRepo.PaymentOrderRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductSizeRowsRepo;
import com.coder.springjwt.services.customerServices.orderPaymentService.OrderPaymentService;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderPaymentServiceImple implements OrderPaymentService {

    @Autowired
    private CustomerAddressRepo customerAddressRepo;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private ProductSizeRowsRepo productSizeRowsRepo;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private OrderPaymentServiceHelper orderPaymentServiceHelper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createOrder(Double amount, long addressId, List<CartItemsDto> cartItems) {
        log.info("<--- createOrder  Flying -->");
        try {

            //USER DATA
            Map<String, String> currentUser = userHelper.getCurrentUser();
            String username = currentUser.get("username");
            User user = this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Fount"));

            //validate Address
            CustomerAddress customerAddress = this.addressWhereAreYou(addressId , user);
            log.info("Customer Address : " + customerAddress);

            //CART VALIDATION
            boolean isValidCart = this.validateCartItems(cartItems);
            log.info("Shopping Cart Validate Success");

            if(isValidCart) {

                //CREATE ORDER BY RAZORPAY
                Order razorpayOrder = this.razorpayService.createRazorpayOrder(amount);


                //Save Data to database
                JSONObject orderData = new JSONObject(razorpayOrder.toString());
                PaymentOrders paymentOrders = new PaymentOrders();
                paymentOrders.setCurrency("INR");
                paymentOrders.setStatus(PaymentState.CREATED.toString());
                paymentOrders.setAmount(String.valueOf(amount));
                paymentOrders.setOrderId(orderData.getString("id"));
                paymentOrders.setCreated_at(String.valueOf(orderData.get("created_at")));
                paymentOrders.setAttempts(String.valueOf(orderData.getInt("attempts")));
                paymentOrders.setPaymentCreatedJson(razorpayOrder.toString());
                paymentOrders.setAddressId(addressId);

                //Payment Provider
                paymentOrders.setPaymentProvider("RAZORPAY");

                //SET PAYMENT MODE STATUS
                paymentOrders.setPaymentMode(PaymentModeStatus.ONLINE.toString());

                //Set Users Data
                paymentOrders.setUserName(user.getUsername());
                paymentOrders.setUserId(String.valueOf(user.getId()));

                //ORDER REF NUMBER
                String orderReferenceNumber = OrderPaymentServiceHelper.generateOrderReferenceNumber();
                paymentOrders.setOrderReferenceNo(orderReferenceNumber);

                //save Data to DB....
                this.paymentOrderRepo.save(paymentOrders);
                log.info("ORDER-ID Saved | Payment Pending");
                return ResponseGenerator.generateSuccessResponse(orderData.toString(), "SUCCESS");
            }else{
                return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    public CustomerAddress addressWhereAreYou(long addressId , User user)
    {
        CustomerAddress customerAddress = this.customerAddressRepo.findByUserIdAndId(user.getId(), addressId);
        if(customerAddress != null)
        {
            return customerAddress;
        }else{
            throw new RuntimeException(CustMessageResponse.ADDRESS_NOT_FOUND + user.getId());
        }
    }

    @Override
    public ResponseEntity<?> orderUpdate(OrderPaymentDto orderPaymentDto) {
        log.info("***** Order Update Flying *****");
        try {

            //USER
            Map<String, String> currentUser = userHelper.getCurrentUser();
            String username = currentUser.get("username");
            User user = this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Fount"));

            //Validate Cart Items
            if(!validateCartItems(orderPaymentDto.getCartItems())){
                throw new RuntimeException("CART_ITEMS_SOMETHING_WRONG");
            }

            //GET PAYMENT ORDER
            PaymentOrders paymentOrders = this.paymentOrderRepo.findByOrderId(orderPaymentDto.getRazorpay_order_id());

            if (paymentOrders == null) {
                throw new RuntimeException(CustMessageResponse.ORDER_ID_NOT_FOUND);
            }

            if( paymentOrders != null || paymentOrders.getOrderId() != "" )
            {
                //CART ITEMS SAVED TO DATABASE...
                this.saveCustomerCartItems(orderPaymentDto.getRazorpay_order_id()
                                                            ,orderPaymentDto.getCartItems()
                                                            ,paymentOrders,
                                                            user);

                // REMOVE CART ITEMS – ObjectMapper Issue
                orderPaymentDto.setCartItems(null);

                //Payment Transaction Updated
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonPayload = objectMapper.writeValueAsString(orderPaymentDto);
                paymentOrders.setPaymentId(orderPaymentDto.getRazorpay_payment_id());
                paymentOrders.setSignature(orderPaymentDto.getRazorpay_signature());
                paymentOrders.setStatus(PaymentState.PAID.toString());
                paymentOrders.setPaymentCompleteJson(jsonPayload);
                this.paymentOrderRepo.save(paymentOrders);

                log.info("ORDER-ID -> " + orderPaymentDto.getRazorpay_order_id());
                log.info("PAYMENT COMPLETE SUCCESS");
                return ResponseGenerator.generateSuccessResponse(CustMessageResponse.PAYMENT_PAID_SUCCESS ,
                        CustMessageResponse.SUCCESS);
            }else{
                throw new RuntimeException(CustMessageResponse.ORDER_ID_NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse( CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }



    public  void saveCustomerCartItems(String razorpayOrderId ,
                                    List<CartItemsDto> cartItemsList,
                                    PaymentOrders paymentOrders,
                                    User user){

        try {

            CustomerAddress customerAddress = this.addressWhereAreYou(paymentOrders.getAddressId(), user);

            List<OrderItems> orderItemsList = new ArrayList<>();
            for(CartItemsDto orderDto :  cartItemsList)
            {
                Map<String, String> sellerInfo = this.getSellerInfo(orderDto.getPId());

                String orderNoPerItems = orderPaymentServiceHelper.generateOrderIdPerItem();

                OrderItems orderItems = new OrderItems();
                orderItems.setProductId(orderDto.getPId());
                orderItems.setProductName(orderDto.getPName());
                orderItems.setProductPrice(orderDto.getPPrice());
                orderItems.setProductBrand(orderDto.getPBrand());
                orderItems.setProductSize(orderDto.getPSize());
                orderItems.setQuantity(String.valueOf(orderDto.getQuantity()));
                orderItems.setTotalPrice(String.valueOf(orderDto.getTotalPrice()));
                orderItems.setFileUrl(orderDto.getPFileUrl());
                orderItems.setProductColor(orderDto.getPColor());
                orderItems.setProductMrp(String.valueOf(orderDto.getPMrp()));
                orderItems.setProductDiscount(orderDto.getPCalculatedDiscount());
                orderItems.setOrderId(razorpayOrderId);

                orderItems.setUserId(String.valueOf(user.getId()));
                orderItems.setUsername(String.valueOf(user.getUsername()));
                orderItems.setPaymentState(PaymentState.PAID.toString());
                orderItems.setPaymentMode(PaymentMode.ONLINE.toString());
                orderItems.setOrderReferenceNo(paymentOrders.getOrderReferenceNo());

                //GENERATE ORDER ID PER-ITEMS
                orderItems.setOrderNoPerItem(orderNoPerItems);

                //SELLER USER-ID AND USERNAME
                orderItems.setSellerId(sellerInfo.get("seller_id"));
                orderItems.setSellerUsername(sellerInfo.get("seller_username"));

                //ORDER SHIPPING ADDRESS DETAILS
                orderItems.setAddressId(customerAddress.getId());
                orderItems.setCountry(customerAddress.getCountry());
                orderItems.setCustomerName(customerAddress.getCustomerName());
                orderItems.setMobileNumber(customerAddress.getMobileNumber());
                orderItems.setArea(customerAddress.getArea());
                orderItems.setPostalCode(customerAddress.getPostalCode());
                orderItems.setAddressLine1(customerAddress.getAddressLine1());
                orderItems.setAddressLine2(customerAddress.getAddressLine2());
                orderItems.setDefaultAddress(customerAddress.isDefaultAddress());
                orderItems.setUsername(customerAddress.getUsername());
                orderItems.setCity(customerAddress.getCity());
                orderItems.setState(customerAddress.getState());

                //ORDER SHIPPING ADDRESS
                OrderShippingAddress sa = this.buildShippingAddress(customerAddress,
                                                                    orderNoPerItems ,
                                                                    razorpayOrderId ,
                                                                    paymentOrders.getOrderReferenceNo());
                //SHIPPING ADDRESS
                orderItems.setOrderShippingAddresses(sa);

                //PAYMENT ORDERS
                orderItems.setPaymentOrders(paymentOrders);

                //ADD ORDER ITEM TO LIST
                orderItemsList.add(orderItems);
            }

            //SET ORDER ITEM LIST TO PAYMENT ORDERS
            paymentOrders.setOrderItems(orderItemsList);

            this.paymentOrderRepo.save(paymentOrders);
            log.info("Order Item Saved Success...");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed Saving Order Items: ", e);
            throw new RuntimeException("FAILED_TO_SAVE_CART_ITEMS");
        }
    }



    public Map<String,String> getSellerInfo(String productId)
    {
        try {
            Map<String,String> map = new HashMap<>();
            ProductDetailsModel productDetails = this.productDetailsRepo.findById(Long.parseLong(productId))
                    .orElseThrow(() -> new DataNotFoundException("Seller Not Found"));
            map.put("seller_username" , productDetails.getUsername());
            map.put("seller_id" , productDetails.getUserId());
            return map;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    public boolean validateCartItems(@NotNull List<CartItemsDto> cartItems) {
        System.out.println(cartItems.toString());
        int orderTotalPrice = 0;
        int cartTotalPrice = 0;

        for (CartItemsDto ci : cartItems) {
            boolean isValid =Boolean.FALSE;

            ProductSizeRows productSize = this.productSizeRowsRepo.
                    findByProductDetailsIdAndMsValNative(Long.parseLong(ci.getPId()) , ci.getPSize())
                    .orElseThrow(()-> new DataNotFoundException("ProductSize Not Found Exception"));


            if(productSize.getPrice().equals(String.valueOf(ci.getPPrice()))
                        && productSize.get__msVal().equals(ci.getPSize())
                        && Integer.parseInt(productSize.getInventory()) > 0 )
            {
               orderTotalPrice += Integer.parseInt(productSize.getPrice()) * ci.getQuantity();
               cartTotalPrice += Integer.parseInt(ci.getPPrice()) * ci.getQuantity();
               isValid = Boolean.TRUE;
                log.info("Product Carts Matched Success ");
            }

            if(!isValid)
            {
                throw new RuntimeException(CustMessageResponse.PRICE_SIZE_AND_INVENTORY_NOT_MATCHED);
            }
        }

        if(String.valueOf(orderTotalPrice).equals(cartTotalPrice)){
            log.info("Variant Total Price :: " + orderTotalPrice);
            log.info("Cart Total Price :: " + cartTotalPrice);
        }

        if(orderTotalPrice != cartTotalPrice)
        {
            throw new RuntimeException(CustMessageResponse.ORDER_TOTAL_AND_CART_TOTAL_NOT_MATCHED);
        }
        return Boolean.TRUE;
    }



    public OrderShippingAddress buildShippingAddress(CustomerAddress customerAddress,
                                                     String orderNoPerItems,
                                                     String razorpayOrderId,
                                                     String orderReferenceNo) {

        OrderShippingAddress sa = new OrderShippingAddress();

        sa.setAddressId(customerAddress.getId());
        sa.setCountry(customerAddress.getCountry());
        sa.setCustomerName(customerAddress.getCustomerName());
        sa.setMobileNumber(customerAddress.getMobileNumber());
        sa.setArea(customerAddress.getArea());
        sa.setPostalCode(customerAddress.getPostalCode());
        sa.setAddressLine1(customerAddress.getAddressLine1());
        sa.setAddressLine2(customerAddress.getAddressLine2());
        sa.setCity(customerAddress.getCity());
        sa.setState(customerAddress.getState());
        sa.setUsername(customerAddress.getUsername());
        sa.setUserId(customerAddress.getUserId());

        // Generate Order ID per item
        sa.setOrderNoPerItem(orderNoPerItems);

        //Razorpay Order-Id
        sa.setOrderId(razorpayOrderId);

        //reference Number
        sa.setOrderReferenceNo(orderReferenceNo);

        return sa;
    }




























//    public boolean validateCartItems(@NotNull List<CartItemsDto> cartItems) {

//        int variantTotalPrice = 0;
//        int cartTotalPrice = 0;
//
//        for (CartItemsDto ci : cartItems) {
//            Optional<ProductDetailsModel> productOpt = this.productDetailsRepo.findById(Long.parseLong(ci.getPId()));
//
//            if (productOpt.isEmpty()) {
//                return Boolean.FALSE; // Product not found
//            }
//
//            ProductDetailsModel sellerProduct = productOpt.get();
//            List<ProductSizeRows> productSizes = sellerProduct.getProductSizeRows();
//
//            boolean isValid =Boolean.FALSE;
//            for(ProductSizeRows pv : productSizes)
//            {
//                if(pv.getPrice().equals(String.valueOf(ci.getPPrice()))
//                        && pv.get__msVal().equals(ci.getPSize())
//                        && Integer.parseInt(pv.getInventory()) > 0 )
//                {
//                    variantTotalPrice += Integer.parseInt(pv.getPrice()) * ci.getQuantity();
//                    cartTotalPrice += Integer.parseInt(ci.getPPrice()) * ci.getQuantity();
//                    isValid = Boolean.TRUE;
//
//                    log.info("Product Carts Matched Success ");
//                }
//                else{
//                    log.error("Product Carts Matched Failed.. ");
//                }
//            }
//            if(!isValid)
//            {
//                throw new RuntimeException(CustMessageResponse.PRICE_SIZE_AND_INVENTORY_NOT_MATCHED);
//            }
//        }
//
//        log.info("Variant Total Price :: " + variantTotalPrice);
//        log.info("Cart Total Price :: " + cartTotalPrice);
//
//        if(variantTotalPrice != cartTotalPrice)
//        {
//            throw new RuntimeException(CustMessageResponse.VARIENT_TOTAL_AND_CART_TOTAL_NOT_MATCHED);
//        }
//        return Boolean.TRUE; // All items are valid
//    }
}
