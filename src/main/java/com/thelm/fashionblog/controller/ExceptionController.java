package com.thelm.fashionblog.controller;

import com.thelm.fashionblog.exception.CustomNotFoundException;
import com.thelm.fashionblog.exception.PostAlreadyLikedException;
import com.thelm.fashionblog.exception.EntityAlreadyExistException;
import com.thelm.fashionblog.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Object> customNotFoundException(CustomNotFoundException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }

    @ExceptionHandler(PostAlreadyLikedException.class)
    public ResponseEntity<Object> postAlreadyLikedException(PostAlreadyLikedException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), FORBIDDEN);
        return new ResponseEntity<>(exceptionResponse, FORBIDDEN);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<Object> userAlreadyExistException(EntityAlreadyExistException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), FORBIDDEN);
        return new ResponseEntity<>(exceptionResponse, FORBIDDEN);
    }
}
