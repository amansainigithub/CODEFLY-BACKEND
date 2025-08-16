package com.coder.springjwt.services.adminServices.catalogNetQuantityService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductNetQuantityDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity.ProductNetQuantityModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.ProductNetQuantityRepo;
import com.coder.springjwt.services.adminServices.catalogNetQuantityService.ProductNetQuantityService;
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
public class ProductNetQuantityServiceImple implements ProductNetQuantityService {

    @Autowired
    private ProductNetQuantityRepo productNetQuantityRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveNetQuantity(ProductNetQuantityDto productNetQuantityDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductNetQuantityModel productNetQuantityModel =  modelMapper.map(productNetQuantityDto, ProductNetQuantityModel.class);
            log.info("Object Mapper Convert Success");

            this.productNetQuantityRepo.save(productNetQuantityModel);
            log.info("NetQuantity Saved Success");

            response.setMessage("NetQuantity Saved Success");
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
    public ResponseEntity<?> deleteNetQuantity(long netQuantityId) {
        try {
            ProductNetQuantityModel productNetQuantityModel = this.productNetQuantityRepo.findById(netQuantityId).orElseThrow(
                    () -> new CategoryNotFoundException("Net Quantity id not Found"));

            this.productNetQuantityRepo.deleteById(productNetQuantityModel.getId());
            log.info("Delete Success => netQuantity id :: " + netQuantityId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("netQuantityId Could Not be deleted or not found");
            return ResponseGenerator.generateBadRequestResponse
                    ("netQuantityId Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getNetQuantityById(long netQuantityId) {
        try {
            ProductNetQuantityModel productNetQuantityModel = this.productNetQuantityRepo.findById(netQuantityId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductNetQuantityDto productNetQuantityDto = modelMapper.map(productNetQuantityModel, ProductNetQuantityDto.class);
            log.info("Hsn Code Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(productNetQuantityDto, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Failed To fetch netQuantity By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateNetQuantity(ProductNetQuantityDto productNetQuantityDto) {
        try {
            log.info("Update NetQuantity Process Starting....");
            this.productNetQuantityRepo.findById(productNetQuantityDto.getId())
                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            ProductNetQuantityModel productNetQuantityModel =  modelMapper.map(productNetQuantityDto, ProductNetQuantityModel.class);
            this.productNetQuantityRepo.save(productNetQuantityModel);

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
    public ResponseEntity<?> getNetQuantity(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductNetQuantityModel> catalogNetQuantityModels = this.productNetQuantityRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogNetQuantityModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + ProductNetQuantityServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductNetQuantityServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogNetQuantityModels, AdminMessageResponse.SUCCESS);
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
