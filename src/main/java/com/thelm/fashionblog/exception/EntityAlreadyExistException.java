package com.thelm.fashionblog.exception;

public class EntityAlreadyExistException extends RuntimeException{
    private String message;

    public EntityAlreadyExistException(String message) {
        this.message = message;
    }
}
