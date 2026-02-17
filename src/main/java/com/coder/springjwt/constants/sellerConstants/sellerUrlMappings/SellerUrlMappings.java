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
    public static final String GET_CHARGES_BY_SELLER = "/getChargesBySeller/{id}";


    //    =======SELLER PRODUCT DETAILS CONTROLLER==========
    public static final String PRODUCT_CONTROLLER = SELLER_AUTH_URL + "/productController";
    public static final String SAVE_PRODUCT_DETAILS = "/saveProductDetails/{variantId}";
    public static final String SAVE_PRODUCT_FILES = "/saveProductFiles/{productId}";



//    =======SELLER VARIANT PRODUCT DETAILS CONTROLLER==========
    public static final String PRODUCT_VARIANT_CONTROLLER = SELLER_AUTH_URL + "/productVariantController";
    public static final String LOAD_PRODUCT_DETAILS = "/loadProductDetails/{productId}";
    public static final String SAVE_PRODUCT_VARIANT_DETAILS = "/saveProductVariantDetails/{variantId}/{productId}";
    public static final String SAVE_PRODUCT_VARIANT_FILES = "/saveProductVariantFiles/{newProductId}/{existingProductId}";




    //    =======PRODUCT OVERVIEW CONTROLLER==========
    public static final String PRODUCT_OVERVIEW_CONTROLLER = SELLER_AUTH_URL + "/productOverviewController";
    public static final String GET_UNDER_REVIEW_PRODUCT = "/getUnderReviewProduct";
    public static final String GET_APPROVED_PRODUCT = "/getApprovedProduct";
    public static final String GET_DIS_APPROVED_PRODUCT = "/getDisApprovedProduct";
    public static final String GET_DRAFT_PRODUCT = "/getDraftProduct";
    public static final String FETCH_ALL_USER_PRODUCTS = "/fetchAllUserProduct";



    //    =======PRODUCTS INVENTORY MANAGER==========
    public static final String INVENTORY_CONTROLLER = SELLER_AUTH_URL + "/inventoryController";
    public static final String GET_ALL_INVENTORY = "/getAllInventory";
    public static final String GET_OUT_OF_STOCK_PRODUCT = "/getOutOfStockProduct";
    public static final String GET_LOW_STOCK_PRODUCT = "/getLowStockProduct";
    public static final String UPDATE_PRODUCT_INVENTORY = "/updateProductInventory";



    //    =======PRODUCTS FILES CONTROLLER==========
    public static final String PRODUCT_FILES_HANDLER_CONTROLLER = SELLER_AUTH_URL + "/productFilesHandlerController";
    public static final String GET_PRODUCT_FILES_BY_ID_SELLER = "/getProductFilesByIdSeller/{productId}/{username}";
    public static final String MODIFIED_PRODUCT_FILES_BY_SELLER = "modifiedProductFilesBySeller/{fileId}/{productId}/{username}";
    public static final String MODIFIED_PRODUCT_VIDEO_FILES_BY_SELLER = "modifiedProductVideoFilesBySeller/{fileId}/{productId}/{username}";
    public static final String UPLOAD_NEW_FILE_BY_SELLER = "uploadNewFileBySeller/{productId}/{username}";






    //ORDER CONTROLLER
    public static final String ORDERS_CONTROLLER = SELLER_AUTH_URL +"/ordersController";
    public static final String GET_PENDING_ORDERS = "/getPendingOrders";
    public static final String GET_CONFIRMED_ORDERS = "/getConfirmedOrders";
    public static final String GET_SHIPPED_ORDERS = "/getShippedOrders";
    public static final String GET_DELIVERED_ORDERS = "/getDeliveredOrders";
    public static final String GET_CALCELLED_ORDERS = "/getCancelledOrders";



    //ORDER ACTION CONTROLLER
    public static final String ORDERS_ACTION_CONTROLLER = SELLER_AUTH_URL +"/ordersActionController";

    public static final String ORDER_ACCEPT = "/orderAccept";




    //    CASH FREE CONTROLLER
    public static final String CASH_FREE_CONTROLLER = SELLER_AUTH_URL +"/cashFreeController";
    public static final String CREATE_CASHFREE_ORDER = "/createCashFreeOrder";


}
