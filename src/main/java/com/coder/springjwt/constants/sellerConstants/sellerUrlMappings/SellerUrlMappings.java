package com.coder.springjwt.constants.sellerConstants.sellerUrlMappings;

public class SellerUrlMappings {

    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/shopping";

    //############## NOT-AUTH BASE URL #####################
    public static final String SELLER_PUBLIC_URL = APPLICATION_CONTEXT_PATH + "/api/seller/v1";

    public static final String SELLER_AUTH_URL = APPLICATION_CONTEXT_PATH + "/api/seller/fly/v1";



    //=======================================CONTROLLER=========================================================

    public static final String SELLER_AUTH_CONTROLLER = SELLER_PUBLIC_URL + "/sellerAuthController";

    public static final String STATE_CITY_PINCODE_CONTROLLER = SELLER_PUBLIC_URL + "/stateCityPincodeController";

    public static final String SELLER_TAX_CONTROLLER = SELLER_PUBLIC_URL + "/sellerTaxController";

    public static final String SELLER_PICKUP_CONTROLLER = SELLER_PUBLIC_URL + "/sellerPickupController";

    public static final String SELLER_BANK_CONTROLLER = SELLER_PUBLIC_URL + "/sellerBankController";

    public static final String SELLER_STORE_CONTROLLER = SELLER_PUBLIC_URL + "/sellerStoreController";



    //=======================================API's=========================================================
    //SignIn API's
    public static final String SELLER_SIGN_IN = "/sellerSignIn";
    public static final String SELLER_SEND_OTP = "/sellerSendOtp";
    public static final String VALIDATE_SELLER_OTP = "/validateSellerOtp";
    public static final String SELLER_SIGN_UP = "/sellerSignup";
    public static final String STATE_CITY_PINCODE = "/stateCityPincode/{pincode}";
    public static final String SELLER_TAX = "/sellerTax";



    //Seller pickup Data
    public static final String SELLER_PICKUP = "/sellerPickup";
    public static final String SELLER_BANK = "/sellerBank";
    public static final String SELLER_STORE = "/sellerStore";


//    =============================================================/
    public static final String PRODUCT_CATEGORY_CONTROLLER = SELLER_AUTH_URL + "/productCategoryController";
    public static final String GET_ROOT_CATEGORY = "/getRootCategory";
    public static final String GET_SUB_CATEGORY = "/getSubCategory/{id}";
    public static final String GET_TYPE_CATEGORY = "/getTypeCategory/{id}";
    public static final String GET_VARIANT_CATEGORY = "/getVariantCategory/{id}";

    //    =============================================================/
    public static final String ENGINE_X_BUILDER_CONTROLLER = SELLER_AUTH_URL + "/engineXBuilderController";
    public static final String GET_ENGINE_X = "/getEngineX/{engineXId}";


//    =======SELLER PRODUCT DETAILS CONTROLLER==========
    public static final String PRODUCT_CONTROLLER = SELLER_AUTH_URL + "/productController";
    public static final String SAVE_PRODUCT_DETAILS = "/saveProductDetails/{variantId}";

    public static final String SAVE_PRODUCT_FILES = "/saveProductFiles/{productId}";


}
