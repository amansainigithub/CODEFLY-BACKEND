package com.coder.springjwt.constants.sellerConstants.sellerEmailConstants;

import org.springframework.stereotype.Component;

@Component
public class SellerEmailConstants {


    public static String registrationCompleted()
    {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Registration Successful</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .email-container {\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "            max-width: 600px;\n" +
                "            margin: auto;\n" +
                "        }\n" +
                "        .email-header {\n" +
                "            text-align: center;\n" +
                "            border-bottom: 1px solid #dddddd;\n" +
                "            padding-bottom: 20px;\n" +
                "        }\n" +
                "        .email-header h1 {\n" +
                "            color: #4CAF50;\n" +
                "        }\n" +
                "        .email-body {\n" +
                "            padding-top: 20px;\n" +
                "        }\n" +
                "        .email-body p {\n" +
                "            font-size: 16px;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "        .email-footer {\n" +
                "            text-align: center;\n" +
                "            font-size: 12px;\n" +
                "            color: #aaaaaa;\n" +
                "            padding-top: 20px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 16px;\n" +
                "            color: #ffffff;\n" +
                "            background-color: #4CAF50;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <div class=\"email-header\">\n" +
                "            <h1>Registration Successful</h1>\n" +
                "        </div>\n" +
                "        <div class=\"email-body\">\n" +
                "            <p>Dear {{name}},</p>\n" +
                "            <p>Thank you for registering on our platform. We are excited to have you onboard!</p>\n" +
                "            <p>Your registration was successful, and you can now log in to your account using the following link:</p>\n" +
                "            <a href=\"{{loginUrl}}\" class=\"button\">Log In to Your Account</a>\n" +
                "        </div>\n" +
                "        <div class=\"email-footer\">\n" +
                "            <p>If you did not sign up for this account, please ignore this email or contact support.</p>\n" +
                "            <p>&copy; 2024 Your Company. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }


}
