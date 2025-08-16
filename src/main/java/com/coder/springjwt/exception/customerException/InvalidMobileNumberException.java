package com.coder.springjwt.exception.customerException;

public class InvalidMobileNumberException extends RuntimeException{

    public InvalidMobileNumberException(String message) {
        super(message);
    }
}
