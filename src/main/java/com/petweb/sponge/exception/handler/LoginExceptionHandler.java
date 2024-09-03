package com.petweb.sponge.exception.handler;

import com.petweb.sponge.exception.error.LoginIdError;
import com.petweb.sponge.exception.error.LoginTypeError;
import com.petweb.sponge.exception.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.petweb.sponge.exception.ExceptionCode.*;

@RestControllerAdvice
public class LoginExceptionHandler {
    @ExceptionHandler(LoginTypeError.class)
    public ResponseEntity<ResponseError> handleLoginTypeException() {
        return new ResponseEntity<>(new ResponseError(NOT_AUTHORIZATION.getCode(), NOT_AUTHORIZATION.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LoginIdError.class)
    public ResponseEntity<ResponseError> handleLoginIdException() {
        return new ResponseEntity<>(new ResponseError(NOT_AUTHORIZATION.getCode(), NOT_AUTHORIZATION.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
