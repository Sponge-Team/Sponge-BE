package com.petweb.sponge.exception.handler;

import com.petweb.sponge.exception.ResponseError;
import com.petweb.sponge.exception.error.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.petweb.sponge.exception.ExceptionCode.NOT_FOUND_ACCOUNT;
import static com.petweb.sponge.exception.ExceptionCode.NOT_FOUND_ENTITY;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler({NotFoundUser.class, NotFoundTrainer.class})
    public ResponseEntity<ResponseError> handleNotFoundAccountException() {
        return new ResponseEntity<>(new ResponseError(NOT_FOUND_ACCOUNT.getCode(), NOT_FOUND_ACCOUNT.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotFoundPet.class, NotFoundAnswer.class, NotFoundPost.class})
    public ResponseEntity<ResponseError> handleNotFoundEntityException() {
        return new ResponseEntity<>(new ResponseError(NOT_FOUND_ENTITY.getCode(), NOT_FOUND_ENTITY.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
