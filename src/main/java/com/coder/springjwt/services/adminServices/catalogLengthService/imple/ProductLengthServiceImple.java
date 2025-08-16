package com.coder.springjwt.services.adminServices.catalogLengthService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductLengthDto;
import com.coder.springjwt.models.adminModels.catalog.catalogLength.ProductLengthModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.ProductLengthRepo;
import com.coder.springjwt.services.adminServices.catalogLengthService.ProductLengthService;
import com.coder.springjwt.services.adminServices.catalogMaterialService.imple.ProductMaterialServiceImple;
import com.coder.springjwt.services.sellerServices.sellerStoreService.userService.userServiceImple.UserServiceImple;
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
public class ProductLengthServiceImple implements ProductLengthService {

    @Autowired
    private ProductLengthRepo productLengthRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<?> saveLength(ProductLengthDto productLengthDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductLengthModel catalogLengthMode =  modelMapper.map(productLengthDto, ProductLengthModel.class);
            log.info("Object Mapper Convert Success");

            this.productLengthRepo.save(catalogLengthMode);
            log.info("Catalog Length Saved Success");

            response.setMessage("Catalog Length Saved Success");
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
    public ResponseEntity<?> getLength(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductLengthModel> catalogLengthModels = this.productLengthRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogLengthModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + ProductMaterialServiceImple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogLengthModels, AdminMessageResponse.SUCCESS);
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
