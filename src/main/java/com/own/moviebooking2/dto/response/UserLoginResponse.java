package com.own.moviebooking2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String username;
    private String accessToken;
    private String refreshToken;
    private String expiresIn;
    private List<String> roles;
    private String type = "Bearer";
}
