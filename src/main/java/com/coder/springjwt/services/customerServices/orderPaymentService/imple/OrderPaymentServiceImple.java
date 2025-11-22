package com.coder.springjwt.services.customerServices.orderPaymentService.imple;

import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.dtos.customerPayloads.cartItemDtos.CartItemsDto;
import com.coder.springjwt.dtos.customerPayloads.orderPaymentDto.OrderPaymentDto;
import com.coder.springjwt.emuns.customer.PaymentModeStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerModels.customerAddressModel.CustomerAddress;
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
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Override
    public ResponseEntity<?> createOrder(Double amount, long addressId, List<CartItemsDto> cartItems) {
        log.info("<--- createOrder  Flying -->");
        try {
            log.info("AMOUNT :: " + amount);
            log.info("ADDRESS ID :: " + addressId);
            log.info("Total Items :: " +cartItems.size());

            Map<String, String> currentUser = userHelper.getCurrentUser();
            String username = currentUser.get("username");
            User user = this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Fount"));


            //validate Address
            CustomerAddress customerAddress = this.addressWhereAreYou(addressId , user);
            log.info("Customer Address : " + customerAddress);

            boolean isValidCart = this.validateCartItems(cartItems);
            log.info("Shopping Cart Validate Success");

            if(isValidCart) {

                //Create Razorpay Order
                RazorpayClient client = new RazorpayClient("rzp_test_cFBctGmM8MII0E", "VgYZ2olzRXQfv87xzvakT9Va");
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", amount * 100);
                orderRequest.put("currency", "INR");

                //Create Order
                Order order = client.Orders.create(orderRequest);
                log.info("***** Razorpay order created Success *******");

                //Save Data to database
                JSONObject orderData = new JSONObject(order.toString());
                log.info("Order Created Data :: " + orderData);
                PaymentOrders paymentOrders = new PaymentOrders();
                paymentOrders.setCurrency("INR");
                paymentOrders.setStatus("CREATED");
                paymentOrders.setAmount(String.valueOf(amount));
                paymentOrders.setOrderId(orderData.getString("id"));
                paymentOrders.setCreated_at(String.valueOf(orderData.get("created_at")));
                paymentOrders.setAttempts(String.valueOf(orderData.getInt("attempts")));
                paymentOrders.setPaymentCreatedJson(order.toString());

                //SET PAYMENT MODE STATUS
                paymentOrders.setPaymentMode(PaymentModeStatus.ONLINE.toString());

                //Set Users Data
                paymentOrders.setUserName(user.getUsername());
                paymentOrders.setUserId(String.valueOf(user.getId()));

                //Generate OrderId
                String customOrderId = OrderPaymentServiceHelper.generateCustomOrderID();
                paymentOrders.setCustomOrderNumber(customOrderId);

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

    @Override
    public ResponseEntity<?> orderUpdate(OrderPaymentDto orderPaymentDto) {
        log.info("***** Order Update Flying *****");
        try {
            PaymentOrders paymentOrders = this.paymentOrderRepo.findByOrderId(orderPaymentDto.getRazorpay_order_id());

            if(paymentOrders != null)
            {
//                IMPORTANT LOGIC************************
                //save Cart Items To Database Final Added
//                this.saveCustomerOrderItems(paymentTransactionPayload.getRazorpay_order_id()
//                        ,paymentTransactionPayload.getCartItems()
//                        ,paymentsTransactions.getCustomOrderNumber());
 //                IMPORTANT LOGIC************************


                orderPaymentDto.setCartItems(null); //cart Items Null Because of Object mapper can't Convert Items

                //Payment Transaction Updated
                paymentOrders.setPaymentId(orderPaymentDto.getRazorpay_payment_id());
                paymentOrders.setSignature(orderPaymentDto.getRazorpay_signature());
                paymentOrders.setStatus("PAID");
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonPayload = objectMapper.writeValueAsString(orderPaymentDto);
                paymentOrders.setPaymentCompleteJson(jsonPayload);
                this.paymentOrderRepo.save(paymentOrders);

                log.info("PAYMENT COMPLETE SUCCESS");
                log.info("ORDER-ID -> " + orderPaymentDto.getRazorpay_order_id());
                log.info("Order Payment Complete Update Success");

                //Update Customer Order
                //this.updateCustomerOrderStatus(paymentTransactionPayload);
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
