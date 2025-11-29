package com.coder.springjwt.constants.adminConstants.adminUrlMappings;

public class AdminUrlMappings {


    //ADMIN MAPPING START
    //############## APP CONTEXT #####################
    public static final String APPLICATION_CONTEXT_PATH = "/shopping";

    //############## AUTH BASE URL #####################
    //Publically Allow
    public static final String ADMIN_PUBLIC_URL = APPLICATION_CONTEXT_PATH + "/api/admin/auth";
    //Protected URL's
    public static final String ADMIN_AUTHORIZE_URL = APPLICATION_CONTEXT_PATH + "/api/admin/flying/v1";



    //SignIn Public URL's

    public static final String ADMIN_AUTH_CONTROLLER = ADMIN_PUBLIC_URL +"/adminAuthController";
    public static final String ADMIN_SIGN_IN = "/adminSignin";
    public static final String  ADMIN_PASS_KEY= "/adminPassKey";
    public static final String  ADMIN_SIGN_UP= "/adminSignUp";

    //ADMIN MAPPING ENDING



    //ROOT CATEGORY
    public static final String ROOT_CATEGORY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/rootCategoryController";
    public static final String  CREATE_ROOT_CATEGORY= "/createRootCategory";
    public static final String  GET_ROOT_CATEGORY_LIST= "/getRootCategoryList";
    public static final String  DELETE_ROOT_CATEGORY_BY_ID= "/deleteRootCategoryById/{categoryId}";
    public static final String  GET_ROOT_CATEGORY_BY_ID= "/getRootCategoryById/{categoryId}";
    public static final String  UPDATE_ROOT_CATEGORY= "/updateRootCategory";
    public static final String UPDATE_ROOT_CATEGORY_FILE="/updateRootCategoryFile/{rootCategoryId}";


    //Sub Category Controller
    public static final String SUB_CATEGORY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/subCategoryController";
    public static final String  SAVE_SUB_CATEGORY= "/saveSubCategory";
    public static final String  GET_SUB_CATEGORY_LIST= "/getSubCategoryList";
    public static final String  DELETE_SUB_CATEGORY_BY_ID= "/deleteSubCategoryById/{categoryId}";
    public static final String  GET_SUB_CATEGORY_BY_ID= "/getSubCategoryById/{categoryId}";
    public static final String  UPDATE_SUB_CATEGORY= "/updateSubCategory";
    public static final String UPDATE_SUB_CATEGORY_FILE="/updateSubCategoryFile/{subCategoryId}";



    //Type Category Controller
    public static final String TYPE_CATEGORY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/TypeCategoryController";
    public static final String  SAVE_TYPE_CATEGORY= "/saveTypeCategory";
    public static final String  GET_TYPE_CATEGORY_LIST= "/getTypeCategoryList";
    public static final String  DELETE_TYPE_CATEGORY_BY_ID= "/deleteTypeCategoryById/{categoryId}";
    public static final String  UPDATE_TYPE_CATEGORY= "/updateTypeCategory";
    public static final String  GET_TYPE_CATEGORY_BY_ID= "/getTypeCategoryById/{categoryId}";
    public static final String UPDATE_TYPE_CATEGORY_FILE="/updateTypeCategoryFile/{typeCategoryId}";




    //Variant Category Controller
    public static final String VARIANT_CATEGORY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/variantCategoryController";
    public static final String  SAVE_VARIANT_CATEGORY= "/saveVariantCategory";
    public static final String  GET_VARIANT_CATEGORY_LIST= "/getVariantCategoryList";
    public static final String  DELETE_VARIANT_CATEGORY_BY_ID= "/deleteVariantCategoryById/{categoryId}";
    public static final String  UPDATE_VARIANT_CATEGORY= "/updateVariantCategory";
    public static final String  GET_VARIANT_CATEGORY_BY_ID= "/getVariantCategoryById/{categoryId}";
    public static final String UPDATE_VARIANT_CATEGORY_FILE="/updateVariantCategoryFile/{variantCategoryId}";





    //FETCH USER's
    public static final String USERS_CONTROLLER = ADMIN_AUTHORIZE_URL +"/usersController";
    public static final String GET_CUSTOMER_BY_PAGINATION="/getCustomerByPagination";
    public static final String GET_SELLER_BY_PAGINATION="/getSellerByPagination";
    public static final String GET_ADMIN_BY_PAGINATION="/getAdminByPagination";



    //HSL URL's and Controller
    public static final String PRODUCT_HSN_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productHsnController";
    public static final String  SAVE_HSN= "/saveHsn";
    public static final String  DELETE_HSN= "/deleteHsnCode/{hsnCodeId}";
    public static final String  GET_HSN_CODE_BY_ID= "/getHsnCodeById/{hsnCodeId}";
    public static final String  UPDATE_HSN_CODE= "/updateHsnCode";
    public static final String GET_HSN_LIST_BY_PAGINATION="/getHsnListByPagination";



    //Size URL's and Controller
    public static final String PRODUCT_SIZE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productSizeVariantController";
    public static final String  SAVE_SIZE= "/saveSize";
    public static final String  DELETE_SIZE= "/deleteSize/{sizeId}";
    public static final String  GET_SIZE_BY_ID= "/getSizeById/{sizeId}";
    public static final String  UPDATE_SIZE= "/updateSize";
    public static final String GET_SIZE="/getSize";


    //PRODUCT MATERIAL URL's and Controller
    public static final String PRODUCT_MATERIAL_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productMaterialController";
    public static final String  SAVE_MATERIAL= "/saveMaterial";
    public static final String  DELETE_MATERIAL= "/deleteMaterial/{materialId}";
    public static final String  GET_MATERIAL_BY_ID= "/getMaterialById/{materialId}";
    public static final String  UPDATE_MATERIAL= "/updateMaterial";
    public static final String GET_MATERIAL="/getMaterial";


    //PRODUCT TYPE URL's and Controller
    public static final String PRODUCT_TYPE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productTypeController";
    public static final String  SAVE_TYPE= "/saveType";
    public static final String  DELETE_TYPE= "/deleteType/{typeId}";
    public static final String  GET_TYPE_BY_ID= "/getTypeById/{typeId}";
    public static final String  UPDATE_TYPE= "/updateType";
    public static final String GET_TYPE="/getType";


    //PRODUCT BRAND URL's and Controller
    public static final String PRODUCT_BRAND_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productBrandController";
    public static final String  SAVE_BRAND= "/saveBrand";
    public static final String  DELETE_BRAND= "/deleteBrand/{brandId}";
    public static final String  GET_BRAND_BY_ID= "/getBrandById/{brandId}";
    public static final String  UPDATE_BRAND= "/updateBrand";
    public static final String  GET_BRAND="/getBrand";


    //CATALOG NET_QUANTITY URL's and Controller
    public static final String PRODUCT_NET_QUANTITY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productNetQuantityController";
    public static final String  SAVE_NET_QUANTITY= "/saveNetQuantity";
    public static final String  DELETE_NET_QUANTITY= "/deleteNetQuantity/{netQuantityId}";
    public static final String  GET_NET_QUANTITY_ID= "/getNetQuantityById/{netQuantityId}";
    public static final String  UPDATE_NET_QUANTITY= "/updateNetQuantity";
    public static final String GET_NET_QUANTITY="/getNetQuantity";



    //GSTPercentage Controller
    public static final String GST_PERCENTAGE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/gstPercentageController";
    public static final String  SAVE_GST_PERCENTAGE= "/saveGstPercentage";
    public static final String GET_GST_PERCENTAGE="/getGstPercentage";




    //Catalog Weight Controller
    public static final String PRODUCT_WEIGHT_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productWeightController";

    public static final String  SAVE_WEIGHT= "/saveWeight";
    public static final String GET_WEIGHT="/getWeight";


    //Catalog Weight Controller
    public static final String PRODUCT_LENGTH_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productLengthController";
    public static final String  SAVE_LENGTH= "/saveLength";
    public static final String GET_LENGTH="/getLength";


    //Catalog Weight Controller
    public static final String PRODUCT_BREATH_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productBreathController";
    public static final String  SAVE_BREATH= "/saveBreath";
    public static final String GET_BREATH="/getBreath";


    //Catalog Weight Controller
    public static final String PRODUCT_HEIGHT_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productHeightController";
    public static final String  SAVE_HEIGHT= "/saveHeight";
    public static final String GET_HEIGHT="/getHeight";


    //CHARGE CONFIG CONTROLLER
    public static final String CHARGE_CONFIG_CONTROLLER = ADMIN_AUTHORIZE_URL +"/chargeConfigController";
    public static final String  CREATE_CHARGE_CONFIG= "/createChargeConfig";
    public static final String  GET_CHARGE_CONFIG_LIST= "/getChargeConfigList";
    public static final String  DELETE_CHARGE_CONFIG= "/deleteChargeConfig/{chargeId}";
    public static final String  GET_CHARGE_CONFIG_BY_ID= "/getChargeConfigById/{chargeId}";
    public static final String  UPDATE_CHARGE_CONFIG= "/updateChargeConfig";




    //PRODUCT UNDER-REVIEW STAGES CONTROLLER
    public static final String PRODUCT_UNDER_REVIEW_STAGE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productUnderReviewStageController";
    public static final String  PRODUCT_UNDER_REVIEW_STAGE= "/productUnderReviewStage";


    //PRODUCT APPROVED STAGES CONTROLLER
    public static final String PRODUCT_APPROVED_STAGE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productApprovedStageController";
    public static final String  PRODUCT_APPROVED_STAGE= "/productApprovedStage";

    //PRODUCT DIS-APPROVED STAGES CONTROLLER
    public static final String PRODUCT_DIS_APPROVED_STAGE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productDisApprovedStageController";
    public static final String  PRODUCT_DIS_APPROVED_STAGE= "/productDisApprovedStage";

    //PRODUCT DIS-APPROVED STAGES CONTROLLER
    public static final String PRODUCT_DRAFT_STAGE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productDraftStageController";
    public static final String  PRODUCT_DRAFT_STAGE= "/productDraftStage";

    //PRODUCT BLOCKED STAGES CONTROLLER
    public static final String PRODUCT_BLOCKED_STAGE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productBlockedStageController";
    public static final String  PRODUCT_BLOCKED_STAGE= "/productBlockedStage";




    //MANAGE PRODUCT BY ADMIN CONTROLLER
    public static final String PRODUCT_MANAGER_CONTROLLER = ADMIN_AUTHORIZE_URL + "/productManagerController";
    public static final String RETRIEVE_PRODUCT_DETAILS = "/retrieveProductDetails/{productId}";


//    ENGINE-X BUILDER ADMIN CONTROLLER
    public static final String ENGINE_X_BUILDER_ADMIN_CONTROLLER = ADMIN_AUTHORIZE_URL + "/engineXBuilderAdminController";
    public static final String GET_ENGINE_X_BY_ADMIN = "/getEngineXByAdmin/{engineXId}";
    public static final String UPDATE_PRODUCT_DETAILS = "/updateProductDetails/{productId}";


    //PRODUCT APPROVAL CONTROLLER
    public static final String PRODUCT_APPROVAL_CONTROLLER = ADMIN_AUTHORIZE_URL + "/productApprovalController";
    public static final String PRODUCT_APPROVED = "/productApproved/{productId}";
    public static final String PRODUCT_DIS_APPROVED = "/productDisApproved/{productId}/{reasonId}/{description}/{rejectionRootCategory}";
    public static final String  GET_REJECTION_REASONS_LIST= "/getRejectionReasonsList";



    //PRODUCT REJECTION REASON CONTROLLER
    public static final String PRODUCT_REJECTION_REASON_CONTROLLER = ADMIN_AUTHORIZE_URL +"/productRejectionReasonController";
    public static final String  CREATE_REJECTION_REASON= "/createRejectionReason";
    public static final String  GET_REJECTION_REASONS= "/getRejectionReasons";
    public static final String  DELETE_REJECTION_REASON= "/deleteRejectionReason/{rejectionId}";
    public static final String  GET_REJECTION_REASON_BY_ID= "/getRejectionReasonById/{rejectionId}";
    public static final String  UPDATE_REJECTION_REASON= "/updateRejectionReason";
    public static final String  FIND_BY_ROOT_REJECTION_CATEGORY= "/findByRootRejectionCategory/{rejectionId}";


    //ROOT REJECTION REASON CONTROLLER
    public static final String ROOT_REJECTION_CATEGORY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/rootRejectionCategoryController";
    public static final String  CREATE_ROOT_REJECTION_CATEGORY= "/createRootRejectionCategory";
    public static final String  GET_ROOT_REJECTION_CATEGORY= "/getRootRejectionCategory";
    public static final String  DELETE_ROOT_REJECTION_CATEGORY= "/deleteRootRejectionCategory/{rejectionId}";
    public static final String  GET_ROOT_REJECTION_CATEGORY_BY_ID= "/getRootRejectionCategoryById/{rejectionId}";
    public static final String  UPDATE_ROOT_REJECTION_CATEGORY = "/updateRootRejectionCategory";


    //EMAIL TEMPLATE CONTROLLER
    public static final String EMAIL_TEMPLATE_CONTROLLER = ADMIN_AUTHORIZE_URL +"/emailTemplateController";
    public static final String  CREATE_EMAIL_TEMPLATE= "/createEmailTemplate";
    public static final String  DELETE_EMAIL_TEMPLATE= "/deleteEmailTemplate/{templateId}";
    public static final String  GET_EMAIL_TEMPLATE= "/getEmailTemplate/{templateId}";
    public static final String  UPDATE_EMAIL_TEMPLATE= "/updateEmailTemplate";
    public static final String GET_EMAIL_TEMPLATE_DATA="/getEmailTemplateData";


    public static final String PRODUCT_FILES_MANAGER_CONTROLLER = ADMIN_AUTHORIZE_URL + "/productFilesManagerController";
    public static final String  GET_PRODUCT_FILES_BY_ID= "/getProductFilesById/{productId}";
    public static final String  UPLOAD_FILES= "/uploadFiles/{productFileId}/{productId}";





    //SLIDER CONTROLLER
    public static final String SLIDER_CONTROLLER = ADMIN_AUTHORIZE_URL +"/sliderController";
    public static final String CREATE_SLIDER= "/createSlider";
    public static final String GET_SLIDERS_LIST= "/getSlidersList";
    public static final String  GET_SLIDER_BY_ID= "/getSliderById/{sliderId}";
    public static final String  UPDATE_SLIDER= "/updateSlider";
    public static final String  DELETE_SLIDER_BY_ID= "/deleteSliderById/{sliderId}";
    public static final String UPDATE_SLIDER_FILE="/updateSliderFile/{sliderId}";



    public static final String RAZORPAY_CONTROLLER = ADMIN_AUTHORIZE_URL +"/razorpayController";
    public static final String CREATE_RAZORPAY_ORDER = "/createRazorpayOrder/{amount}";
    public static final String FETCH_RAZORPAY_ORDER_DETAILS = "/fetchRazorpayOrderDetails" + "/{orderId}";

    public static final String FETCH_RAZORPAY_PAYMENT_DETAILS = "/fetchRazorpayPaymentDetails" + "/{paymentId}";
    public static final String VERIFY_RAZORPAY_SIGNATURE = "/verifyRazorpaySignature";

}
