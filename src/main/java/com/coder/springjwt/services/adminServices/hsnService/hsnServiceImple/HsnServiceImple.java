package com.coder.springjwt.services.adminServices.hsnService.hsnServiceImple;

import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.hsn.HsnCodesDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.repository.adminRepository.catalogRepos.HsnRepository;
import com.coder.springjwt.services.adminServices.hsnService.HsnCodeService;
import com.coder.springjwt.services.sellerServices.sellerStoreService.userService.userServiceImple.UserServiceImple;
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
public class HsnServiceImple implements HsnCodeService {


    @Autowired
    private HsnRepository hsnRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger  = LoggerFactory.getLogger(HsnServiceImple.class);

    @Override
    public ResponseEntity<?> saveHsn(HsnCodesDto hsnCodesDto) {
        MessageResponse response =new MessageResponse();
        try {
            HsnCodes hsnCodes=  modelMapper.map(hsnCodesDto , HsnCodes.class);
            logger.info("Object Mapper Convert Success");

                this.hsnRepository.save(hsnCodes);
                logger.info("HSN Saved Success");

                response.setMessage("HSN Saved Success");
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
    public ResponseEntity<?> deleteHsnCode(long hsnCodeId) {
            try {
                HsnCodes hsnCode = this.hsnRepository.findById(hsnCodeId).orElseThrow(
                        () -> new CategoryNotFoundException("HSN Code id not Found"));

                this.hsnRepository.deleteById(hsnCode.getId());
                logger.info("Delete Success => HSN id :: " + hsnCodeId );
                return ResponseGenerator.generateSuccessResponse("Delete Success" , AdminMessageResponse.SUCCESS);

            }
            catch (Exception e)
            {
                e.printStackTrace();
                logger.error("HSN Code Could Not be deleted");
                return ResponseGenerator.generateBadRequestResponse
                        ("HSN Could not deleted :: " + e.getMessage() , AdminMessageResponse.FAILED);
            }
        }

    @Override
    public ResponseEntity<?> getHsnCodeById(long hsnCodeId) {
        try {
            HsnCodes  hsnCodes= this.hsnRepository.findById(hsnCodeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            HsnCodesDto hsnCodesDto = modelMapper.map(hsnCodes, HsnCodesDto.class);
            logger.info("Hsn Code Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(hsnCodesDto , AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Failed To fetch Hsn Code By Id");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , AdminMessageResponse.FAILED);
        }
    }

    @Override
    public ResponseEntity<?> updateHsnCode(HsnCodesDto hsnCodesDto) {
        try {
            logger.info("Update Child Process Starting....");
            log.info("ID :: "  + hsnCodesDto.getId());
            this.hsnRepository.findById(hsnCodesDto.getId())
                                    .orElseThrow(()->new DataNotFoundException("Data not Found"));

            HsnCodes hsnCodes =  modelMapper.map(hsnCodesDto , HsnCodes.class);
            this.hsnRepository.save(hsnCodes);

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
    public ResponseEntity<?> getHsnCodesPagination(Integer page, Integer size) {
        MessageResponse response = new MessageResponse();
        try {
            Page<HsnCodes> hsnCodes = this.hsnRepository.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
            if(hsnCodes.isEmpty())
            {
                log.info("Data Not found :::: {} " + UserServiceImple.class.getName());

                response.setStatus(HttpStatus.BAD_GATEWAY);
                response.setMessage(AdminMessageResponse.DATA_NOT_FOUND);
                return ResponseGenerator.generateBadRequestResponse(response, AdminMessageResponse.DATA_NOT_FOUND);
            }else{
                log.info("Data fetch Success :::: {}" + UserServiceImple.class.getName());

                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(hsnCodes, AdminMessageResponse.SUCCESS);
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
