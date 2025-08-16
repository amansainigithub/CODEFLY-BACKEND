package com.coder.springjwt.services.adminServices.gstPercentageService.imple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.GstPercentageDto;
import com.coder.springjwt.models.adminModels.catalog.gstPercentage.GstPercentageModel;
import com.coder.springjwt.repository.adminRepository.catalogRepos.GstPercentageRepo;
import com.coder.springjwt.services.adminServices.catalogBrandService.imple.ProductBrandServiceImple;
import com.coder.springjwt.services.adminServices.gstPercentageService.GstPercentageService;
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
public class GstPercentageServiceImple implements GstPercentageService {

    @Autowired
    private GstPercentageRepo gstPercentageRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveGstPercentage(GstPercentageDto gstPercentageDto) {
        MessageResponse response =new MessageResponse();
        try {
            GstPercentageModel gstPercentageModel=  modelMapper.map(gstPercentageDto , GstPercentageModel.class);
            log.info("Object Mapper Convert Success");

            this.gstPercentageRepo.save(gstPercentageModel);
            log.info("gstPercentage Saved Success");

            response.setMessage("Gst Percentage Saved Success");
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
    public ResponseEntity<?> getGstPercentage(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<GstPercentageModel> gstPercentageModels = this.gstPercentageRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(gstPercentageModels.isEmpty())
            {
                log.info("Data Not found :::: {} " + GstPercentageServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + ProductBrandServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(gstPercentageModels, AdminMessageResponse.SUCCESS);
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
