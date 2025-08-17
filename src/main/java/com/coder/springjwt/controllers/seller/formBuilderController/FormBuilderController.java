package com.coder.springjwt.controllers.seller.formBuilderController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.sellerFormBuilderService.SellerFormBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_FORM_BUILDER_CONTROLLER)
public class FormBuilderController {

    @Autowired
    private SellerFormBuilderService sellerFormBuilderService;


    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(SellerUrlMappings.GET_FORM_BUILDER)
    public FormRootCapture getFormBuilder() {
        return sellerFormBuilderService.getFormBuilder();
    }
}
