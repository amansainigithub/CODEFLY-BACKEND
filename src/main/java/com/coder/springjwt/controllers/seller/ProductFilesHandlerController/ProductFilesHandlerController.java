package com.coder.springjwt.controllers.seller.ProductFilesHandlerController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.productFilesHandlerService.ProductFilesHandlerService;
import com.coder.springjwt.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_FILES_HANDLER_CONTROLLER)
public class ProductFilesHandlerController {

    @Autowired
    private ProductFilesHandlerService productFilesHandlerService;


    @GetMapping(SellerUrlMappings.GET_PRODUCT_FILES_BY_ID_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getProductFilesByIdSeller(@PathVariable long productId, @PathVariable String username) {
        return this.productFilesHandlerService.getProductFilesByIdSeller(productId , username);
    }


    @PostMapping(SellerUrlMappings.MODIFIED_PRODUCT_FILES_BY_SELLER)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> modifiedProductFilesBySeller( @RequestParam("files") MultipartFile files,
                                                           @PathVariable String fileId,
                                                           @PathVariable String productId,
                                                           @PathVariable String username ) {
        return this.productFilesHandlerService.modifiedProductFilesBySeller(files , fileId ,productId , username);
    }


}
