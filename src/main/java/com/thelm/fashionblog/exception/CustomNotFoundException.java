package com.thelm.fashionblog.exception;

import lombok.Getter;

@Getter
public class CustomNotFoundException extends RuntimeException{
    private String message;

    public CustomNotFoundException(String message) {
        this.message = message;
    }
}
