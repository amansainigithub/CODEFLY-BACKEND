package com.coder.springjwt.services.sellerServices.sellerProductService.imple;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductBrandDto;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductRoot;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductRootRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductSizeRowsRepo;
import com.coder.springjwt.services.sellerServices.sellerProductService.ProductService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImple implements ProductService {

    @Autowired
    private  VariantCategoryRepo variantCategoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private ProductSizeRowsRepo  productSizeRowsRepo;

    @Autowired
    private ProductRootRepo productRootRepo;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> saveProductDetails(ProductDetailsDto productDetailsDto, long variantId) {
        log.info("saveProductDetails........");
        try {
            VariantCategoryModel variantCategoryModel = checkVariantId(variantId);

            if (variantCategoryModel == null) {
                return ResponseGenerator.generateBadRequestResponse();
            }

            if(variantCategoryModel != null)
            {
                log.info("Variant Data");
                log.info("Variant ID ::" + variantCategoryModel.getId());
                log.info("Variant Category Name :: " + variantCategoryModel.getCategoryName());

                //Get User
                Map<String,String> node =  UserHelper.getCurrentUser();
                User username = this.getUserDetails(node.get("username"));

                //Product Root
                ProductRoot productRoot = new ProductRoot();
                productRoot.setVariantId(variantCategoryModel.getId());
                productRoot.setVariantName(variantCategoryModel.getCategoryName());

                //Set UserId and UserName
                productRoot.setUserId(String.valueOf(username.getId()));
                productRoot.setUsername(String.valueOf(username.getUsername()));

                //Convert Data To mapper
                ProductDetailsModel productDetailsModel = modelMapper.map(productDetailsDto, ProductDetailsModel.class);
                productDetailsModel.setVariantId(variantCategoryModel.getId());
                productDetailsModel.setVariantName(variantCategoryModel.getCategoryName());
                productDetailsModel.setProductSeries("MAIN");
                //Set UserId and UserName
                productDetailsModel.setUserId(String.valueOf(username.getId()));
                productDetailsModel.setUsername(String.valueOf(username.getUsername()));

                //Set Product Root to Product Details Model
                productDetailsModel.setProductRoot(productRoot);

                //Set Product-Details To Product-Root Entity
                productRoot.setProductDetailsModels(List.of(productDetailsModel));

                //Set Product-Details to Product Size Rows
                for(ProductSizeRows productSizeRows : productDetailsModel.getProductSizeRows())
                {
                    productSizeRows.setProductDetailsModel(productDetailsModel);
                    //Set UserId and UserName
                    productSizeRows.setUserId(String.valueOf(username.getId()));
                    productSizeRows.setUsername(String.valueOf(username.getUsername()));
                }

                //save Data----
                ProductRoot productData = this.productRootRepo.save(productRoot);

                Map<Object,Object> mapNode = new HashMap<>();
                mapNode.put("id",productData.getId());
                return ResponseGenerator.generateSuccessResponse(mapNode,"Success");
            }else{
                return ResponseGenerator.generateBadRequestResponse();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }


    public VariantCategoryModel checkVariantId(long variantId)
    {
        try {
            VariantCategoryModel variantCategoryData = this.variantCategoryRepo.findById(variantId)
                    .orElseThrow(() -> new DataNotFoundException("Variant Category Id not found :: " + variantId));
           return variantCategoryData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




    private User getUserDetails(String username){
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(()-> new UsernameNotFoundException("User Not Found.."));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
