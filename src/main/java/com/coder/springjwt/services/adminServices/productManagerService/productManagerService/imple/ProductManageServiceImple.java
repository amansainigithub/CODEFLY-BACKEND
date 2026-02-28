package com.coder.springjwt.services.adminServices.productManagerService.productManagerService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductSizeRowsDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.models.adminModels.chargeConfigModels.ChargeConfig;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.repository.adminRepository.chargeConfigRepo.ChargeConfigRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductRootRepo;
import com.coder.springjwt.services.adminServices.productManagerService.productManagerService.ProductManageService;
import com.coder.springjwt.services.sellerServices.sellerProductService.imple.ProductServiceHelper;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
public class ProductManageServiceImple implements ProductManageService {

    @Autowired
    private ProductDetailsRepo productDetailsRepo;
    @Autowired
    private ProductServiceHelper productServiceHelper;
    @Autowired
    private ProductRootRepo productRootRepo;
    @Autowired
    private ChargeConfigRepo chargeConfigRepo;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private VariantCategoryRepo variantCategoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> retrieveProductDetails(long productId) {
        try {
            log.info("Get Type Category Data...");
            ProductDetailsModel productDetails = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Data Not Found PRODUCT ID :: " + productId));
            return ResponseGenerator.generateSuccessResponse(productDetails , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Error in Seller get Product Category ");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateProductDetails(ProductDetailsDto productDetailsDto, long productId) {
        log.info("==================CAPTURE DETAILS=========================");
        log.info(productDetailsDto.toString());

        try {
            ProductDetailsModel productData = productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not Found :: " + productId));

            //CHARGE CONFIG TCS, TDS, SHIPPING CHARGE
            ChargeConfig chargeConfig = this.chargeConfigRepo.findByVariantId(String.valueOf(productData.getVariantId()))
                    .orElseThrow(() -> new DataNotFoundException("Variant Id Not Found ID :: " + productData.getVariantId()));

            if (productData != null) {
                //Get User
                Map<String, String> node = userHelper.getCurrentUser();
                User username = this.getUserDetails(node.get("username"));

                // Update only required fields (don’t create new object)
                productData.setProductName(productDetailsDto.getProductName());
                productData.setDefaultName(productDetailsDto.getDefaultName());
                productData.setGst(productDetailsDto.getGst());
                productData.setHsnCode(productDetailsDto.getHsnCode());
                productData.setNetWeight(productDetailsDto.getNetWeight());
                productData.setProductSizes(productDetailsDto.getProductSizes());
                productData.setColor(productDetailsDto.getColor());
                productData.setNetQuantity(productDetailsDto.getNetQuantity());
                productData.setNeck(productDetailsDto.getNeck());
                productData.setOccasion(productDetailsDto.getOccasion());
                productData.setPattern(productDetailsDto.getPattern());
                productData.setSleeveLength(productDetailsDto.getSleeveLength());
                productData.setCountryOfOrigin(productDetailsDto.getCountryOfOrigin());
                productData.setManufacturerName(productDetailsDto.getManufacturerName());
                productData.setManufacturerAddress(productDetailsDto.getManufacturerAddress());
                productData.setManufacturerPincode(productDetailsDto.getManufacturerPincode());
                productData.setBrand(productDetailsDto.getBrand());
                productData.setLining(productDetailsDto.getLining());
                productData.setClosureType(productDetailsDto.getClosureType());
                productData.setStretchType(productDetailsDto.getStretchType());
                productData.setCareInstruction(productDetailsDto.getCareInstruction());
                productData.setDescription(productDetailsDto.getDescription());

                //SAVE FORMAT PRODUCT DATE AND TIME
                productData.setProductDate(this.getFormatDate());
                productData.setProductTime(this.getFormatTime());

                //Set Product-Details to Product Size Rows
                String productPrice = null;
                String productMrp = null;
                int count = 0;
                for (ProductSizeRowsDto productSizeRowsDto : productDetailsDto.getProductSizeRows()) {

                    if(count == 0)
                    {
                        productPrice = productSizeRowsDto.getPrice();
                        productMrp = productSizeRowsDto.getMrp();
                    }
                    count++;
                }

                log.info("productPrice :: " + productPrice);
                log.info("productMrp :: " + productMrp);

                productData.setProductPrice(productPrice);
                productData.setProductMrp(productMrp);


                // Handle child list safely
                if (productDetailsDto.getProductSizeRows() != null) {
                    productData.getProductSizeRows().clear();
                    for (ProductSizeRowsDto sizeDto : productDetailsDto.getProductSizeRows()) {
                        ProductSizeRows sizeRow = new ProductSizeRows();


                        //Calculate TAX Information Starting...
                        //GST
                        BigDecimal productGst = productServiceHelper.calculateGST(new BigDecimal(sizeDto.getPrice()),
                                new BigDecimal(productData.getGst()));
                        log.info("PRODUCT GST :: " + productGst);

                        //TCS
                        BigDecimal productTcs = productServiceHelper.calculateTCS(new BigDecimal(sizeDto.getPrice()),
                                new BigDecimal(productData.getGst()) , chargeConfig.getTcsCharge());
                        log.info("PRODUCT TCS :: " + productTcs);

                        //TDS
                        BigDecimal productTds = productServiceHelper.calculateTDS(new BigDecimal(sizeDto.getPrice())
                                ,chargeConfig.getTdsCharge());
                        log.info("PRODUCT TDS :: " + productTds);

                        BigDecimal bankSettlementAmount = productServiceHelper
                                .bankSettlement(new BigDecimal(sizeDto.getPrice()), productGst, productTcs, productTds);
                        //Calculate TAX Information Ending....


                        sizeRow.setPrice(sizeDto.getPrice());
                        sizeRow.setMrp(sizeDto.getMrp());
                        sizeRow.setInventory(sizeDto.getInventory());
                        sizeRow.setSkuCode(sizeDto.getSkuCode());
                        sizeRow.setChestSize(sizeDto.getChestSize());
                        sizeRow.setLengthSize(sizeDto.getLengthSize());
                        sizeRow.setShoulderSize(sizeDto.getShoulderSize());
                        sizeRow.set__msId(sizeDto.get__msId());
                        sizeRow.set__msVal(sizeDto.get__msVal());
                        sizeRow.setUserId(username.getId() + "");
                        sizeRow.setUsername(username.getUsername());
                        sizeRow.setProductDetailsModel(productData);
                        sizeRow.setProductRootId(productData.getProductRootId());
                        sizeRow.setProductKey(productData.getProductKey());

                        //GST , TDS , TCS CHARGES
                        sizeRow.setProductGst(String.valueOf(productGst));
                        sizeRow.setProductTcs(String.valueOf(productTcs));
                        sizeRow.setProductTds(String.valueOf(productTds));
                        sizeRow.setBankSettlementAmount(String.valueOf(bankSettlementAmount));
                        sizeRow.setBankSettlementWithShipping(String.valueOf(bankSettlementAmount));

                        //SHIPPING CHARGES
                        float shippingCharges = Float.parseFloat(chargeConfig.getShippingCharge());
                        float shippingFee = Float.parseFloat(chargeConfig.getShippingChargeFee());
                        float shippingTotal = shippingCharges + shippingFee;
                        sizeRow.setShippingCharges(String.valueOf(shippingCharges));
                        sizeRow.setShippingFee(String.valueOf(shippingFee));
                        sizeRow.setShippingTotal(String.valueOf(shippingTotal));
                        sizeRow.setBankSettlementWithShipping(String.valueOf(bankSettlementAmount.add(BigDecimal.valueOf(shippingTotal))));
                        //SHIPPING CHARGES ENDING...

                        //PRODUCT DISCOUNT %
                        float productDiscount = productServiceHelper.calculateDiscountPercent(Float.parseFloat(sizeDto.getMrp()),
                                Float.parseFloat(sizeDto.getPrice()));
                        sizeRow.setProductDiscount(String.valueOf(productDiscount));


                        productData.getProductSizeRows().add(sizeRow);


                    }
                }

                // Save and return
                ProductDetailsModel saved = productDetailsRepo.save(productData);

                Map<Object, Object> mapNode = new HashMap<>();
                mapNode.put("id", saved.getId());
                return ResponseGenerator.generateSuccessResponse(mapNode, "Success");

            } else {
                return ResponseGenerator.generateBadRequestResponse();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }




    public String getFormatDate() {
        // Current Date
        LocalDate today = LocalDate.now();
        // Formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        // Convert
        String formattedDate = today.format(formatter);
        return formattedDate;
    }



    public String getFormatTime() {
        // Current Time
        LocalTime now = LocalTime.now();
        // Formatter -> h:mm a  (12-hour with AM/PM)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        // Format time
        String formattedTime = now.format(formatter);
        return formattedTime;
    }

    private User getUserDetails(String username) {
        try {
            return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found.."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public VariantCategoryModel checkVariantId(long variantId) {
        try {
            VariantCategoryModel variantCategoryData = this.variantCategoryRepo.findById(variantId).orElseThrow(() -> new DataNotFoundException("Variant Category Id not found :: " + variantId));
            return variantCategoryData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





}
