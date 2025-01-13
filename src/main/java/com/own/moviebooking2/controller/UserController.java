package com.own.moviebooking2.controller;

import com.own.moviebooking2.dto.request.UserLoginRequest;
import com.own.moviebooking2.dto.response.CreateTokenAgainResponse;
import com.own.moviebooking2.dto.response.UserLoginResponse;
import com.own.moviebooking2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/user/signup")
    public String signUp(@RequestBody UserLoginRequest loginRequest) {
        return userService.signUp(loginRequest);
    }

    @PostMapping(value = "/user/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest, response);
    }

    //    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }

    //    @PreAuthorize("hasRole('HR_ROLE')")
    @GetMapping("/hr")
    public ResponseEntity<String> helloHr() {
        return ResponseEntity.ok("Hello hr");
    }

    @PreAuthorize("hasRole('USER_ROLE')")
    @GetMapping("/user")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello User");
    }

    @PostMapping(value = "/user/refresh-token")
    public ResponseEntity<CreateTokenAgainResponse> refreshAccessToken(HttpServletRequest request,
                                                                       HttpServletResponse response) {
        return ResponseEntity.ok(userService.refreshAccessToken(request, response));
    }

    @GetMapping("/get-user")
    public String getUserFromToken(HttpServletRequest request) {
        return userService.getUserFromToken(request);
    }
}
