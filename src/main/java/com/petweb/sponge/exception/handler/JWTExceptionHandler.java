package com.petweb.sponge.exception.handler;

import com.petweb.sponge.exception.ResponseError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class JWTExceptionHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseError> handleSignatureException() {
        return new ResponseEntity<>(new ResponseError(401, "토큰이 유효하지 않습니다."),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseError> handleMalformedJwtException() {
        return new ResponseEntity<>(new ResponseError(401, "올바르지 않은 토큰입니다."),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseError> handleExpiredJwtException() {
        return new ResponseEntity<>(new ResponseError(401, "토큰이 만료되었습니다. 다시 로그인해주세요."),HttpStatus.UNAUTHORIZED);
    }
}
