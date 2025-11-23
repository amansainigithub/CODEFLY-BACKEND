package com.coder.springjwt.services.homePageService.imple;

import com.coder.springjwt.dtos.customerPayloads.homepageDtos.ProductDetailsDataDto;
import com.coder.springjwt.dtos.customerPayloads.homepageDtos.SliderDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.slidersModels.SliderModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sliderRepository.SliderRepository;
import com.coder.springjwt.services.homePageService.HomePageService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HomePageServiceImple implements HomePageService {

    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;


    @Override
    public ResponseEntity<?> fetchHomePage() {
        Map<Object,Object> mapNode = new HashMap<>();
        try {

            //SLIDER DATA
            List<SliderDto> sliderDtoList = new ArrayList<>();
            List<SliderModel> sliders = this.sliderRepository.findBySliderCategoryAndIsActive("HOMEPAGE_SLIDER" , Boolean.TRUE);
            if(sliders.isEmpty()) {
                throw new DataNotFoundException("No slider found | Please check slider Data");
            }

            for(SliderModel sm : sliders)
            {
                SliderDto sliderDto = new SliderDto();
                sliderDto.setTitle(sm.getTitle());
                sliderDto.setSubTitle(sm.getSubTitle());
                sliderDto.setDescription(sm.getDescription());
                sliderDto.setFileUrl(sm.getFileUrl());
                sliderDto.setRoutingLink(sm.getRoutingLink());
                sliderDtoList.add(sliderDto);
            }
            //SLIDER DATA END
            //######################################################################################################

            //PRODUCT DETAILS DATA
            List<ProductDetailsDataDto> productDetailsList = new ArrayList<>();
            List<ProductDetailsModel> productData = this.productDetailsRepo.
                                                    findTop100ByProductStatus(ProductStatus.APPROVED.toString(),
                                                    Sort.by("id").descending());

            for(ProductDetailsModel pdm : productData)
            {
                ProductDetailsDataDto productDetailsDataDto = new ProductDetailsDataDto();
                productDetailsDataDto.setProductId(pdm.getId());
                productDetailsDataDto.setProductName(pdm.getProductName());
                productDetailsDataDto.setDefaultName(pdm.getDefaultName());
                productDetailsDataDto.setManufacturerName(pdm.getManufacturerName());
                productDetailsDataDto.setBrand(pdm.getBrand());
                productDetailsDataDto.setProductDate(pdm.getProductDate());
                productDetailsDataDto.setProductTime(pdm.getProductTime());
                productDetailsDataDto.setProductStatus(pdm.getProductStatus());
                productDetailsDataDto.setPattern(pdm.getPattern());
                productDetailsDataDto.setMrp(pdm.getProductMrp());
                productDetailsDataDto.setPrice(pdm.getProductPrice());

                //Product Size Rows
                ProductSizeRows productSizeRows = pdm.getProductSizeRows().get(0);
                productDetailsDataDto.setInventory(productSizeRows.getInventory());
                productDetailsDataDto.setProductDiscount(productSizeRows.getProductDiscount());

                //Product Files
                ProductFiles productFiles = pdm.getProductFiles().get(0);
                String fileUrl =  productFiles.getFileUrl();
                String fileName = productFiles.getFileName();
                if(fileUrl != null && fileName != null){
                    productDetailsDataDto.setProductMainFileUrl(fileUrl);
                    productDetailsDataDto.setFileName(fileName);
                }


                productDetailsList.add(productDetailsDataDto);
            }
            //PRODUCT DETAILS DATA END
            //######################################################################################################


            mapNode.put("sliderDtoList",sliderDtoList);
            mapNode.put("productDetailsList",productDetailsList);

            return ResponseGenerator.generateSuccessResponse(mapNode,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }
}
