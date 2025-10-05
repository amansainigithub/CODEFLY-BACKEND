package com.coder.springjwt.controllers.admin.productManagerController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.ProductApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_APPROVAL_CONTROLLER)
public class ProductApprovalController {

    @Autowired
    private ProductApprovalService productApprovalService;

    @PostMapping(AdminUrlMappings.PRODUCT_APPROVED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> productApproved(@PathVariable long productId  ) {

        try {
            Thread.sleep(5000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return this.productApprovalService.productApproved( productId );
    }

    @PostMapping(AdminUrlMappings.PRODUCT_DIS_APPROVED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> productDisApproved(@PathVariable long productId  ) {
        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return this.productApprovalService.productDisApproved( productId );
    }

}
