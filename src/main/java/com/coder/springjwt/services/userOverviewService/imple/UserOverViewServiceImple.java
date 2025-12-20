package com.coder.springjwt.services.userOverviewService.imple;

import com.coder.springjwt.dtos.adminDtos.dataOverviewDtos.useroverviewDtos.AdminDataOverviewDto;
import com.coder.springjwt.dtos.adminDtos.dataOverviewDtos.useroverviewDtos.CustomerDataOverviewDto;
import com.coder.springjwt.dtos.adminDtos.dataOverviewDtos.useroverviewDtos.SellerDataOverviewDto;
import com.coder.springjwt.models.User;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.services.userOverviewService.UserOverViewService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserOverViewServiceImple implements UserOverViewService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getCustomerUsersOverview(String searchValue) {
        try {
            // DB se users fetch karo
            List<User> users = userRepository.searchByIdOrUsernameOrMobileAndProjectRole(searchValue ,"ROLE_CUSTOMER");

            // DTO me map karo
            List<CustomerDataOverviewDto> userData = users.stream().map(user -> {
                CustomerDataOverviewDto dto = new CustomerDataOverviewDto();
                dto.setCreationDate(user.getCreationDate());
                dto.setLastModifiedDate(user.getLastModifiedDate());
                dto.setId(user.getId());
                dto.setUsername(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setMobile(user.getMobile());
                dto.setCustomerMobileVerify(user.getCustomerMobileVerify());
                dto.setCustUsername(user.getCustUsername());
                dto.setCustomerMobileOtp(user.getCustomerMobileOtp());
                dto.setProjectRole(user.getProjectRole());
                dto.setCustomerRegisterComplete(user.getCustomerRegisterComplete());
                return dto;
            }).toList();

            return ResponseGenerator.generateSuccessResponse(userData , "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Something went wrong");
        }
    }

    @Override
    public ResponseEntity<?> getAdminUsersOverview(String searchValue) {
        try {
            // DB se users fetch karo
            List<User> users = userRepository.searchByIdOrUsernameOrMobileAndProjectRole(searchValue ,"ROLE_ADMIN");

            // DTO me map karo
            List<AdminDataOverviewDto> userData = users.stream().map(user -> {
                AdminDataOverviewDto dto = new AdminDataOverviewDto();
                dto.setCreationDate(user.getCreationDate());
                dto.setLastModifiedDate(user.getLastModifiedDate());
                dto.setId(user.getId());
                dto.setUsername(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setProjectRole(user.getProjectRole());
                dto.setPassKey(user.getPassKey());
                return dto;
            }).toList();

            return ResponseGenerator.generateSuccessResponse(userData , "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Something went wrong");
        }
    }

    @Override
    public ResponseEntity<?> getSellerUsersOverview(String searchValue) {
        try {
            // DB se users fetch karo
            List<User> users = userRepository.searchByIdOrUsernameOrMobileAndProjectRole(searchValue ,"ROLE_SELLER");

            // DTO me map karo
            List<SellerDataOverviewDto> userData = users.stream().map(user -> {
                SellerDataOverviewDto dto = new SellerDataOverviewDto();
                dto.setCreationDate(user.getCreationDate());
                dto.setLastModifiedDate(user.getLastModifiedDate());
                dto.setId(user.getId());
                dto.setUsername(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setProjectRole(user.getProjectRole());
                dto.setMobile(user.getMobile());
                dto.setSellerMobile(user.getSellerMobile());
                dto.setSellerMobileVerify(user.getSellerMobileVerify());
                dto.setSellerEmailVerify(user.getSellerEmailVerify());
                dto.setSellerRegisterComplete(user.getSellerRegisterComplete());
                dto.setSellerEmail(user.getSellerEmail());
                dto.setSellerStoreName(user.getSellerStoreName());
                return dto;
            }).toList();

            return ResponseGenerator.generateSuccessResponse(userData , "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Something went wrong");
        }
    }

}
