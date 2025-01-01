//package com.own.moviebooking2.exception;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class DefaultExceptionHandler {
//
//    @ExceptionHandler({ AuthenticationException.class })
//    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Token invalid");
//        response.put("details", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Token has expired");
//        response.put("details", ex.getMessage());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }
//}