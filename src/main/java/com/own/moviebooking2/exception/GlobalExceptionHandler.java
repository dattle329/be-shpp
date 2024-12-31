//package com.own.moviebooking2.exception;
//
//import io.jsonwebtoken.MalformedJwtException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler({MalformedJwtException.class})
//    public ResponseEntity<CommonException> exceptionHandler(MalformedJwtException e) {
//        CommonException exception = new CommonException(e.getMessage());
//        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
//    }
//}
