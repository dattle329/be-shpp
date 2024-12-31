package com.own.moviebooking2.exception;

import lombok.Data;

@Data
public class CommonException extends RuntimeException {
    private String message;

    public CommonException(String message) {
        super(message);
        this.message = message;
    }
}
