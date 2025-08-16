package com.coder.springjwt.exception.customerException;

public class InvalidUsernameAndPasswordException extends RuntimeException {

    public InvalidUsernameAndPasswordException(String message) {
        super(message);
    }
}
