package com.coder.springjwt.helpers.ValidateMobNumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateMobileNumber {

    public static boolean isValid(String s)
    {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression for which
        // object of Matcher class is created
        Matcher m = pattern.matcher(s);
        // Returning boolean value
        return (m.matches());
    }
}
