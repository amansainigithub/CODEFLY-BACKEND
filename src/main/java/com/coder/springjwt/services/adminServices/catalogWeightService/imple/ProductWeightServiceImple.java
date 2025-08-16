package com.coder.springjwt.services.adminServices.catalogWeightService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductWeightDto;
import com.coder.springjwt.models.adminModels.catalog.catalogWeight.ProductWeightModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.ProductWeightRepo;
import com.coder.springjwt.services.adminServices.catalogBrandService.imple.ProductBrandServiceImple;
import com.coder.springjwt.services.adminServices.catalogWeightService.ProductWeightService;
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
public class ProductWeightServiceImple implements ProductWeightService {

    @Autowired
    private ProductWeightRepo productWeightRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<?> saveWeight(ProductWeightDto productWeightDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductWeightModel productWeightModel =  modelMapper.map(productWeightDto, ProductWeightModel.class);
            log.info("Object Mapper Convert Success");

            this.productWeightRepo.save(productWeightModel);
            log.info("Catalog Weight Saved Success");

            response.setMessage("Catalog Weight Saved Success");
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
    public ResponseEntity<?> getWeight(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductWeightModel> catalogWeightModels = this.productWeightRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogWeightModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + ProductBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogWeightModels, AdminMessageResponse.SUCCESS);
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
