package com.ratingService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResouceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handlerResourceNotFoundException(ResouceNotFoundException resouceNotFoundException){

        Map<String,Object> map=new HashMap<>();
        map.put("message",resouceNotFoundException.getMessage());
        map.put("Status","Exception Occured");
        return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
    }

}
