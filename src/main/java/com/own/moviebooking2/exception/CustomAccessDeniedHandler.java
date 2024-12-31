//package com.own.moviebooking2.exception;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.nio.file.AccessDeniedException;
//
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
//        response.getWriter().write("{\"message\": \"Access denied: " + accessDeniedException.getMessage() + "\"}");
//    }
//}
