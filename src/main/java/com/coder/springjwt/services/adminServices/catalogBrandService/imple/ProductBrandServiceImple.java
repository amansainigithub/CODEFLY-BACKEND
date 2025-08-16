package com.coder.springjwt.services.adminServices.catalogBrandService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductBrandDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.productBrand.ProductBrandModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.ProductBrandRepo;
import com.coder.springjwt.services.adminServices.catalogBrandService.ProductBrandService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProductBrandServiceImple implements ProductBrandService {

    @Autowired
    private ProductBrandRepo productBrandRepo;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(ProductBrandServiceImple.class);
    @Override
    public ResponseEntity<?> saveBrand(ProductBrandDto productBrandDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductBrandModel productBrandModel =  modelMapper.map(productBrandDto, ProductBrandModel.class);
            logger.info("Object Mapper Convert Success");

            this.productBrandRepo.save(productBrandModel);
            logger.info("Brand Saved Success");

            response.setMessage("Brand Saved Success");
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
    public ResponseEntity<?> deleteBrand(long brandId) {
        try {
            ProductBrandModel productBrandModel = this.productBrandRepo.findById(brandId).orElseThrow(
                    () -> new CategoryNotFoundException("HSN Code id not Found"));

            this.productBrandRepo.deleteById(productBrandModel.getId());
            logger.info("Delete Success => BRAND id :: " + brandId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("BRAND Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("BRAND Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getBrandById(long brandId) {
        try {
            ProductBrandModel productBrandModel = this.productBrandRepo.findById(brandId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductBrandDto productBrandDto = modelMapper.map(productBrandModel, ProductBrandDto.class);
            logger.info("Brand Code Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(productBrandDto, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Brand By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateBrand(ProductBrandDto productBrandDto) {
        try {
            logger.info("Update Brand Process Starting....");
            this.productBrandRepo.findById(productBrandDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductBrandModel productBrandModel =  modelMapper.map(productBrandDto, ProductBrandModel.class);
            this.productBrandRepo.save(productBrandModel);

            logger.info("Data Updated Success");
            return ResponseGenerator.generateSuccessResponse(AdminMessageResponse.SUCCESS ,
                    AdminMessageResponse.DATA_UPDATE_SUCCESS);
        }
        catch (Exception e)
        {
            logger.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(AdminMessageResponse.FAILED ,AdminMessageResponse.DATA_UPDATE_FAILED);
        }

    }

    @Override
    public ResponseEntity<?> getBrand(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductBrandModel> catalogBrandModels = this.productBrandRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogBrandModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + ProductBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogBrandModels, AdminMessageResponse.SUCCESS);
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
