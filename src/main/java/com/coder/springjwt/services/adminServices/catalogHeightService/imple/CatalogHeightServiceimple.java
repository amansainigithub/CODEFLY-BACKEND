package com.coder.springjwt.services.adminServices.catalogHeightService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductHeightDto;
import com.coder.springjwt.models.adminModels.catalog.catalogHeight.ProductHeightModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.ProductHeightRepo;
import com.coder.springjwt.services.adminServices.catalogHeightService.CatalogHeightService;
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
public class CatalogHeightServiceimple implements CatalogHeightService {

    @Autowired
    private ProductHeightRepo productHeightRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveHeight(ProductHeightDto productHeightDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductHeightModel productHeightModel =  modelMapper.map(productHeightDto, ProductHeightModel.class);
            log.info("Object Mapper Convert Success");

            this.productHeightRepo.save(productHeightModel);
            log.info("Catalog height Saved Success");

            response.setMessage("Catalog height Saved Success");
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
    public ResponseEntity<?> getHeight(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<ProductHeightModel> catalogHeightModels = this.productHeightRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(catalogHeightModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + CatalogHeightServiceimple.class.getName());
                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(catalogHeightModels, AdminMessageResponse.SUCCESS);
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
