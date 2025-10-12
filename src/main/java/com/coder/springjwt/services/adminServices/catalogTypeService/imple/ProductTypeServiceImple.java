package com.coder.springjwt.services.adminServices.catalogTypeService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductTypeDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogType.ProductTypeModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.ProductTypeRepo;
import com.coder.springjwt.services.adminServices.catalogTypeService.ProductTypeService;
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
public class ProductTypeServiceImple implements ProductTypeService {

    @Autowired
    private ProductTypeRepo productTypeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveType(ProductTypeDto productTypeDto) {

        MessageResponse response =new MessageResponse();
        try {
            ProductTypeModel productTypeModel =  modelMapper.map(productTypeDto, ProductTypeModel.class);
            log.info("Object Mapper Convert Success");

            this.productTypeRepo.save(productTypeModel);
            log.info("Catalog-Type Saved Success");

            response.setMessage("Catalog-Type Saved Success");
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
    public ResponseEntity<?> deleteType(long typeId) {
        try {
            ProductTypeModel productTypeModel = this.productTypeRepo.findById(typeId).orElseThrow(
                    () -> new CategoryNotFoundException("Catalog Type id not Found"));

            this.productTypeRepo.deleteById(productTypeModel.getId());
            log.info("Delete Success => Type id :: " + typeId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Catalog Type Could Not be deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Catalog Type Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getTypeById(long typeId) {
        try {
            ProductTypeModel productTypeModel = this.productTypeRepo.findById(typeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductTypeDto productTypeDto = modelMapper.map(productTypeModel, ProductTypeDto.class);
            log.info("Catalog Type Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(productTypeDto, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch Catalog Type By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateType(ProductTypeDto productTypeDto) {
        try {
            log.info("Update Child Process Starting....");
            this.productTypeRepo.findById(productTypeDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductTypeModel productTypeModel =  modelMapper.map(productTypeDto, ProductTypeModel.class);
            this.productTypeRepo.save(productTypeModel);

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
    public ResponseEntity<?> getType(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductTypeModel> catalogTypeList = this.productTypeRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogTypeList.isEmpty())
            {
                log.info("Data Not found :::: {} " + ProductTypeServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductTypeServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogTypeList, AdminMessageResponse.SUCCESS);
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
