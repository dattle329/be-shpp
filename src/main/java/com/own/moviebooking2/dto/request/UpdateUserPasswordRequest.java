package com.own.moviebooking2.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserPasswordRequest {
    @JsonProperty(value = "old_password")
    private String oldPassword;

    @JsonProperty(value = "new_password")
    private String newPassword;

    @JsonProperty(value = "confirm_password")
    private String confirmPassword;
}
