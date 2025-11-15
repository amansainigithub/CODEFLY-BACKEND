package com.coder.springjwt.services.homePageService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface HomePageService {

    ResponseEntity<?> fetchHomePage();
}
