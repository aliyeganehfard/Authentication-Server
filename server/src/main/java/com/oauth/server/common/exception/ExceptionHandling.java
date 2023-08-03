package com.oauth.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuthException(AuthException authException){
        return new ResponseEntity<>(authException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
