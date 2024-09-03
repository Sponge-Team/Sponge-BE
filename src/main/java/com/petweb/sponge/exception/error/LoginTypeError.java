package com.petweb.sponge.exception.error;

public class LoginTypeError extends RuntimeException {

    // 기본 생성자
    public LoginTypeError() {
        super("훈련사 or 유저만 사용할 수 있습니다.");
    }

    // 메시지를 받는 생성자
    public LoginTypeError(String message) {
        super(message);
    }

    // 메시지와 원인 예외를 받는 생성자
    public LoginTypeError(String message, Throwable cause) {
        super(message, cause);
    }

    // 원인 예외를 받는 생성자
    public LoginTypeError(Throwable cause) {
        super(cause);
    }
}
