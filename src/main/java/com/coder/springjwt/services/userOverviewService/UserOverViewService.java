package com.coder.springjwt.services.userOverviewService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserOverViewService {

    ResponseEntity<?> getCustomerUsersOverview(String searchValue);

    ResponseEntity<?> getAdminUsersOverview(String searchValue);

    ResponseEntity<?> getSellerUsersOverview(String searchValue);
}
