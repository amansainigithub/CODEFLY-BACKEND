package com.coder.springjwt.services.sellerServices.sellerFormBuilderService;

import com.coder.springjwt.controllers.seller.formBuilderController.FormRootCapture;
import org.springframework.stereotype.Component;

@Component
public interface SellerFormBuilderService {

    public FormRootCapture getFormBuilder();
}
