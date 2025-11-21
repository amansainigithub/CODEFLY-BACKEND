package com.coder.springjwt.globalExceptionHandler;

public class AddressLimitExceededException extends  RuntimeException {
    public AddressLimitExceededException(String message) {
        super(message);
    }
}
