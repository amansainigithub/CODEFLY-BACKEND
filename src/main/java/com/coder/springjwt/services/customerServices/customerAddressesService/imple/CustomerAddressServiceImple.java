package com.coder.springjwt.services.customerServices.customerAddressesService.imple;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.dtos.customerPayloads.customerAddressDto.CustomerAddressDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.globalExceptionHandler.AddressLimitExceededException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerModels.customerAddressModel.CustomerAddress;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerAddressRepository.CustomerAddressRepo;
import com.coder.springjwt.services.customerServices.customerAddressesService.CustomerAddressService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CustomerAddressServiceImple implements CustomerAddressService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerAddressRepo customerAddressRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHelper userHelper;

    @Override
    public ResponseEntity<?> saveAddress(CustomerAddressDto customerAddressDto) {
        log.info("<-- saveAddress Flying --> ");
        try {
            CustomerAddress customerAddress = modelMapper.map(customerAddressDto, CustomerAddress.class);

            Map<String, String> currentUser = this.userHelper.getCurrentUser();
            User user = this.userRepository.findByUsername(currentUser.get("username")).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            //Set Address Limit
            long addressCount = this.customerAddressRepo.countByUserId(user.getId());
            if(addressCount >= 50){
                throw new AddressLimitExceededException("NOT_MORE_THAN_50_ADDRESS");
            } else if (addressCount == 0) {
                customerAddress.setDefaultAddress(Boolean.TRUE);
            }

            //Change another Addresses Remove to Default Set Value to FALSE
            if(customerAddress.isDefaultAddress())
            {
                List<CustomerAddress> addressList = this.customerAddressRepo.findByUserIdAndDefaultAddress(user.getId(), Boolean.TRUE);
                addressList.stream().forEach(e->{
                    e.setDefaultAddress(Boolean.FALSE);
                    this.customerAddressRepo.save(e);
                });
            }

            //save Username and Userid
            customerAddress.setUsername(user.getUsername());
            customerAddress.setUserId(user.getId());

            //save User
            customerAddress.setUser(user);

            this.customerAddressRepo.save(customerAddress);

            log.info("saveAddress Finishing Point");
            return ResponseGenerator.generateSuccessResponse(customerAddressDto , "DATA_SAVED_SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }



    @Override
    public ResponseEntity<?> getAddress() {
        log.info("<-- getAddress Flying --> ");
        try {
            Map<String, String> currentUser = this.userHelper.getCurrentUser();
            User user = this.userRepository.findByUsername(currentUser.get("username")).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            List<CustomerAddress> customerAddresses = customerAddressRepo.findByUserId(user.getId());

            List<CustomerAddressDto> addressCollector = customerAddresses.stream().map(
                    ca -> modelMapper.map(ca, CustomerAddressDto.class)).collect(Collectors.toList());
            log.info("<-- getAddress Fetch Success --> ");
            return ResponseGenerator.generateSuccessResponse(addressCollector , CustMessageResponse.SOMETHING_WENT_WRONG);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> deleteAddress(long id) {
        log.info("<-- deleteAddress Flying --> ");

        try {
            Map<String, String> currentUser = this.userHelper.getCurrentUser();
            User user = this.userRepository.findByUsername(currentUser.get("username")).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            //Delete Address
            this.customerAddressRepo.deleteById(id);

            //if Last Address is here so make as a Default Address
            long addressCount = this.customerAddressRepo.countByUserId(user.getId());
            if (addressCount == 1) {
                List<CustomerAddress> customerAddressList = this.customerAddressRepo.findAll();
                customerAddressList.stream().forEach(ca->{
                    ca.setDefaultAddress(Boolean.TRUE);
                    this.customerAddressRepo.save(ca);
                });
            }
            log.info("<-- deleteAddress Success --> ");
            return ResponseGenerator.generateSuccessResponse(id,"DELETE_SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG,
                    "DELETE_FAILED");
        }
    }

    @Override
    public ResponseEntity<?> setDefaultAddress(long id) {
        log.info("<-- setDefaultAddress Flying --> ");

        try {
            Map<String, String> currentUser = this.userHelper.getCurrentUser();
            User user = this.userRepository.findByUsername(currentUser.get("username")).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            List<CustomerAddress> addressList = this.customerAddressRepo.findByUserIdAndDefaultAddress(user.getId(), Boolean.TRUE);
            addressList.stream().forEach(e->{
                e.setDefaultAddress(Boolean.FALSE);
                this.customerAddressRepo.save(e);
            });

            CustomerAddress changeDefaultAddress = this.customerAddressRepo.findById(id)
                    .orElseThrow(()-> new DataNotFoundException(CustMessageResponse.DATA_NOT_FOUND));

            changeDefaultAddress.setDefaultAddress(Boolean.TRUE);
            this.customerAddressRepo.save(changeDefaultAddress);
            log.info("<-- setDefaultAddress Success --> ");
            return ResponseGenerator.generateSuccessResponse("SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> getAddressById(long id) {
        log.info("<-- getAddressById Flying --> ");
        try {
            Map<String, String> currentUser = this.userHelper.getCurrentUser();
            User user = this.userRepository.findByUsername(currentUser.get("username")).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            CustomerAddress customerAddress = this.customerAddressRepo.findByUserIdAndId(user.getId(), id);

            log.info("<-- getAddressById Success --> ");
            return ResponseGenerator.generateSuccessResponse(customerAddress,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> updateAddress(CustomerAddressDto customerAddressDto) {
        log.info("<-- updateAddress Flying --> ");
        try {
            Map<String, String> currentUser = this.userHelper.getCurrentUser();
            User user = this.userRepository.findByUsername(currentUser.get("username")).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            if(customerAddressDto.isDefaultAddress())
            {
                List<CustomerAddress> addressList = this.customerAddressRepo.findByUserIdAndDefaultAddress(user.getId(), Boolean.TRUE);
                addressList.stream().forEach(e->{
                    e.setDefaultAddress(Boolean.FALSE);
                    this.customerAddressRepo.save(e);
                });
            }

            CustomerAddress customerAddress = this.customerAddressRepo.findByUserIdAndId(user.getId(), customerAddressDto.getId());
            customerAddress.setId(customerAddressDto.getId());
            customerAddress.setCountry(customerAddressDto.getCountry());
            customerAddress.setCustomerName(customerAddressDto.getCustomerName());
            customerAddress.setMobileNumber(customerAddressDto.getMobileNumber());
            customerAddress.setArea(customerAddressDto.getArea());
            customerAddress.setPostalCode(customerAddressDto.getPostalCode());
            customerAddress.setCity(customerAddressDto.getCity());
            customerAddress.setState(customerAddressDto.getState());
            customerAddress.setAddressLine1(customerAddressDto.getAddressLine1());
            customerAddress.setAddressLine2(customerAddressDto.getAddressLine2());
            customerAddress.setUser(user);
            customerAddress.setUserId(user.getId());
            customerAddress.setUsername(user.getUsername());
            customerAddress.setDefaultAddress(customerAddressDto.isDefaultAddress());

            this.customerAddressRepo.save(customerAddress);

            log.info(" <-- updateAddress Success --> ");
            return ResponseGenerator.generateSuccessResponse("UPDATE_SUCCESS", "SUCCESS");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

}
