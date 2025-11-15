package com.coder.springjwt.controllers.customer.homePageController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.homePageService.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerUrlMappings.HOME_PAGE_CONTROLLER)
public class HomePageController {

    @Autowired
    private HomePageService homePageService;


    @GetMapping(CustomerUrlMappings.FETCH_HOME_PAGE)
    public ResponseEntity<?> fetchHomePage() {
        return this.homePageService.fetchHomePage();
    }

}
