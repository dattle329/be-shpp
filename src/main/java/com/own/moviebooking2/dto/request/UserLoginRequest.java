package com.own.moviebooking2.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
    private String username;
    private String password;
}
