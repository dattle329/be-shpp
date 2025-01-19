package com.own.moviebooking2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CommonException.class})
    public ResponseEntity<String> exceptionHandler(CommonException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ArithmeticException.class})
    public ResponseEntity<String> exceptionHandler(ArithmeticException e) {
        return new ResponseEntity<>(e.getMessage() + "/" + "can not do that", HttpStatus.BAD_REQUEST);
    }
}
