package com.coder.springjwt.services.sellerServices.productCategoryService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.models.adminModels.categories.RootCategoryModel;
import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import com.coder.springjwt.models.adminModels.categories.TypeCategoryModel;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.RootCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.SubCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.TypeCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.services.sellerServices.productCategoryService.ProductCategoryService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductCategoryServiceImple implements ProductCategoryService {

    @Autowired
    private RootCategoryRepo rootCategoryRepo;

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private TypeCategoryRepo typeCategoryRepo;

    @Autowired
    private VariantCategoryRepo variantCategoryRepo;

    @Override
    public ResponseEntity<?> getRootCategory() {
        try {
            log.info("Get Product Category Data...");
            List<RootCategoryModel> rootCategoryList = this.rootCategoryRepo.findAll();
            return ResponseGenerator.generateSuccessResponse(rootCategoryList , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Error in Seller get Product Category ");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getSubCategory(long id) {
        try {
            log.info("Get Sub Category Data...");
            List<SubCategoryModel> subCategoryList = this.subCategoryRepo.findByRootCategoryId(id);
            return ResponseGenerator.generateSuccessResponse(subCategoryList , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Error in Seller get Product Category ");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getTypeCategory(long id) {
        try {
            log.info("Get Type Category Data...");
            List<TypeCategoryModel> typeCategoryList = this.typeCategoryRepo.findBySubCategoryModelId(id);
            return ResponseGenerator.generateSuccessResponse(typeCategoryList , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Error in Seller get Product Category ");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getVariantCategory(long id) {
        try {
            log.info("Get Type Category Data...");
            List<VariantCategoryModel> variantCategoryList = this.variantCategoryRepo.findByTypeCategoryModelId(id);
            return ResponseGenerator.generateSuccessResponse(variantCategoryList , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Error in Seller get Product Category ");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }
}
