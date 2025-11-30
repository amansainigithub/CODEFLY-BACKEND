package com.coder.springjwt.services.adminServices.catalogSizeService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductSizeVariantDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogSize.ProductSizeVariantModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.ProductSizeRepo;
import com.coder.springjwt.services.adminServices.catalogSizeService.ProductSizeService;
import com.coder.springjwt.services.sellerServices.sellerAuthenticationService.sellerStoreService.userService.userServiceImple.UserServiceImple;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductSizeServiceImple implements ProductSizeService {

    @Autowired
    private ProductSizeRepo productSizeRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<?> saveSize(ProductSizeVariantDto productSizeVariantDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductSizeVariantModel productSizeVariantModel =  modelMapper.map(productSizeVariantDto, ProductSizeVariantModel.class);
            log.info("Object Mapper Convert Success");

            this.productSizeRepo.save(productSizeVariantModel);
            log.info("CATALOG size Saved Success");

            response.setMessage("Catalog Size Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);

        }
        catch (DataIntegrityViolationException ex) {
            response.setMessage("Duplicate entry error: ");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteSize(long sizeId) {
        try {
            ProductSizeVariantModel productSizeVariantModel = this.productSizeRepo.findById(sizeId).orElseThrow(
                    () -> new CategoryNotFoundException("HSN Code id not Found"));

            this.productSizeRepo.deleteById(productSizeVariantModel.getId());
            log.info("Delete Success => Catalog id :: " + sizeId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Catalog Size Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("HSN Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getSizeById(long sizeId) {
        try {
            ProductSizeVariantModel productSizeVariantModel = this.productSizeRepo.findById(sizeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductSizeVariantDto productSizeVariantDto = modelMapper.map(productSizeVariantModel, ProductSizeVariantDto.class);
            log.info("Catalog Size Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(productSizeVariantDto, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch Catalog size By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateSize(ProductSizeVariantDto productSizeVariantDto) {
        try {
            log.info("Update Catalog Size Process Starting....");
            this.productSizeRepo.findById(productSizeVariantDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductSizeVariantModel productSizeVariantModel =  modelMapper.map(productSizeVariantDto, ProductSizeVariantModel.class);
            this.productSizeRepo.save(productSizeVariantModel);

            log.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.SUCCESS ,
                    AdminMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.FAILED ,AdminMessageResponse.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getSize(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductSizeVariantModel> catalogSizeData = this.productSizeRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogSizeData.isEmpty())
            {
                log.info("Data Not found :::: {} " + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductSizeServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogSizeData, AdminMessageResponse.SUCCESS);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.FAILED);
        }
    }
}
