package com.coder.springjwt.controllers.seller.sellerProductCategory;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.productCategoryService.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_CATEGORY_CONTROLLER)
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping(SellerUrlMappings.GET_ROOT_CATEGORY)
    public ResponseEntity<?> getRootCategory() {
        return this.productCategoryService.getRootCategory();
    }

    @GetMapping(SellerUrlMappings.GET_SUB_CATEGORY)
    public ResponseEntity<?> getSubCategory(@PathVariable long id ) {
        return this.productCategoryService.getSubCategory(id);
    }

    @GetMapping(SellerUrlMappings.GET_TYPE_CATEGORY)
    public ResponseEntity<?> getTypeCategory(@PathVariable long id ) {
        return this.productCategoryService.getTypeCategory(id);
    }

    @GetMapping(SellerUrlMappings.GET_VARIANT_CATEGORY)
    public ResponseEntity<?> getVariantCategory(@PathVariable long id ) {
        return this.productCategoryService.getVariantCategory(id);
    }





}
