package com.oauth.commonutils.common.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonUtilsExceptionHandling {

    @ExceptionHandler(CommonUtilsException.class)
    public ResponseEntity<String> handleAuthException(CommonUtilsException commonUtilsException){
        return new ResponseEntity<>(commonUtilsException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
