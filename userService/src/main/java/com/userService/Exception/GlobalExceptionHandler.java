package com.userService.Exception;

import com.userService.Payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResouceNotFoundException resouceNotFoundException){

        String message=resouceNotFoundException.getMessage();
        ApiResponse apiResponseObj=ApiResponse.builder().message(message).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<ApiResponse>(apiResponseObj,HttpStatus.NOT_FOUND);
    }

}
