package com.coder.springjwt.helpers.validateGstRegex;

public class ValidateGstRegex {

    // GSTIN (Goods and Services Tax Identification Number) regex pattern
    private static final String GST_REGEX = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";

    // Method to validate GST number
    public static boolean isValidGstNumberRegex(String gstNumber) {
        if (gstNumber == null) {
            return false;
        }

        // Validate against the regex pattern
        return gstNumber.matches(GST_REGEX);
    }
}
