package com.coder.springjwt.services.productDetailsServices.imple;

import com.coder.springjwt.dtos.customerPayloads.productDetailsCustomerDtos.ProductDetailsCustomerDto;
import com.coder.springjwt.dtos.customerPayloads.productDetailsCustomerDtos.ProductFilesDtos;
import com.coder.springjwt.dtos.customerPayloads.productDetailsCustomerDtos.ProductSizesDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sliderRepository.SliderRepository;
import com.coder.springjwt.services.productDetailsServices.ProductDetailsService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductDetailsServiceImple implements ProductDetailsService {



    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;


    @Override
    public ResponseEntity<?> getProductDetails(Long productId , @PathVariable String productName) {
        Map<Object,Object> mapNode = new HashMap<>();
        try {
            log.info("ProductId:: " +productId);
            log.info("productName:: " +productName);

            //PRODUCT DETAILS DATA
            ProductDetailsModel productDetails = this.productDetailsRepo.
                    findByIdAndProductStatus(productId , ProductStatus.APPROVED.toString()).
                    orElseThrow(()-> new DataNotFoundException("Product id not found OR Product Not Approved ! Error"));

            ProductDetailsCustomerDto productDetailsCustomerDto = new ProductDetailsCustomerDto();
            productDetailsCustomerDto.setProductId(productDetails.getId());
            productDetailsCustomerDto.setProductName(productDetails.getProductName());
            productDetailsCustomerDto.setDefaultName(productDetails.getDefaultName());
            productDetailsCustomerDto.setBrand(productDetails.getBrand());
            productDetailsCustomerDto.setManufacturerName(productDetails.getManufacturerName());
            productDetailsCustomerDto.setProductMrp(productDetails.getProductMrp());
            productDetailsCustomerDto.setProductPrice(productDetails.getProductPrice());
//            productDetailsCustomerDto.setProductDiscount(productDetails.getProductDiscount());
            productDetailsCustomerDto.setProductColor(productDetails.getColor());

            List<ProductFilesDtos> productFilesDtoList = new ArrayList<>();
            List<ProductFiles> productFiles = productDetails.getProductFiles();
            for(ProductFiles pf : productFiles)
            {
                ProductFilesDtos pfDto = new ProductFilesDtos();
                pfDto.setPFileName(pf.getFileName());
                pfDto.setPFileUrl(pf.getFileUrl());
                pfDto.setPFileType(pf.getFileType());
                productFilesDtoList.add(pfDto);
            }

            //MAIN IMAGE FILE
            productDetailsCustomerDto.setProductMainImage(productFiles.get(0).getFileUrl());


            List<ProductSizesDto> productSizesDtoList = new ArrayList<>();
            List<ProductSizeRows> productSizeRows = productDetails.getProductSizeRows();
            for(ProductSizeRows psr : productSizeRows)
            {
                ProductSizesDto productSizesDto = new ProductSizesDto();
                productSizesDto.setProductPrice(psr.getPrice());
                productSizesDto.setProductMrp(psr.getMrp());
                productSizesDto.setProductInventory(psr.getInventory());
                productSizesDto.setProductSize(psr.get__msVal());
                productSizesDtoList.add(productSizesDto);
            }


            //PRODUCT DETAILS DATA END
            //######################################################################################################

            productDetailsCustomerDto.setProductFilesDtos(productFilesDtoList);
            productDetailsCustomerDto.setProductSizesDtos(productSizesDtoList);


            mapNode.put("pw",productDetailsCustomerDto);
            return ResponseGenerator.generateSuccessResponse(mapNode,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }
}
