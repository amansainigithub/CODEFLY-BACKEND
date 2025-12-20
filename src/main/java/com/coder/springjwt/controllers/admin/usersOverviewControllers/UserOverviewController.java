package com.coder.springjwt.controllers.admin.usersOverviewControllers;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.userOverviewService.UserOverViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.USER_OVERVIEW_CONTROLLER)
public class UserOverviewController {

    @Autowired
    private UserOverViewService userOverViewService;

    @GetMapping(AdminUrlMappings.GET_CUSTOMER_USERS_OVERVIEW)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCustomerUsersOverview(@RequestParam String searchValue) {
        return this.userOverViewService.getCustomerUsersOverview(searchValue);
    }


    @GetMapping(AdminUrlMappings.GET_ADMIN_USERS_OVERVIEW)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminUsersOverview(@RequestParam String searchValue) {
        return this.userOverViewService.getAdminUsersOverview(searchValue);
    }


    @GetMapping(AdminUrlMappings.GET_SELLER_USERS_OVERVIEW)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSellerUsersOverview(@RequestParam String searchValue) {
        return this.userOverViewService.getSellerUsersOverview(searchValue);
    }


}
