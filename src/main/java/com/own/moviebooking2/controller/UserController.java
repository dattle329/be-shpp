package com.own.moviebooking2.controller;

import com.own.moviebooking2.dto.request.UpdateUserPasswordRequest;
import com.own.moviebooking2.dto.request.UserLoginRequest;
import com.own.moviebooking2.dto.response.UserLoginResponse;
import com.own.moviebooking2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/user/signup")
    public String registerUser(@RequestBody UserLoginRequest loginRequest) {
        return userService.registerUser(loginRequest);
    }

    @PostMapping(value = "/user/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PutMapping(value = "/user/{userId}/password/update")
    public String updateUserPassword(@RequestBody UpdateUserPasswordRequest request, @PathVariable("userId") Long id) {
        return userService.updateUserPassword(request, id);
    }

    @GetMapping(value = "/test")
    public int test(){
        return 1/0;
    }
}
