package com.own.moviebooking2.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
public class CreateTokenAgainResponse {
    private String accessToken;
    private String refreshToken;
}
