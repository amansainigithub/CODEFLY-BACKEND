package com.coder.springjwt.util;

import com.coder.springjwt.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseGenerator {

    /**
     * This method will return the controller response.
     *
     * @return {@link ResponseEntity} of {@link ResponseDto}
     */

    public static <T> ResponseEntity<ResponseDto<T>> generateSuccessResponse(String message) {
        ResponseDto<T> responseDTO = new ResponseDto(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseDto<T>> generateSuccessResponse(
            T data,String message) {
        ResponseDto<T> responseDTO = new ResponseDto(data, message, true,
                System.currentTimeMillis());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }



    public static <T> ResponseEntity<Object> generateBadRequestResponse() {
        ResponseDto<T> responseDTO = new ResponseDto<>();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

//    public static <T> ResponseEntity<Object> generateBadRequestResponse(T data,String message) {
//        ResponseDto<T> responseDTO = new ResponseDto(data, message, true,
//                HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
//    }

    public static <T> ResponseEntity<Object> generateBadRequestResponse(T data,String message) {
        ResponseDto<T> responseDTO = new ResponseDto(data, message, true,
                System.currentTimeMillis());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<Object> generateBadRequestResponse(T data) {
        ResponseDto<T> responseDTO = new ResponseDto(data);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ResponseDto<T>> generateBadRequestResponse(
            String message) {
        ResponseDto<T> responseDTO = new ResponseDto(Optional.empty(), message, false,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

}
