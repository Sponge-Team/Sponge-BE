package com.petweb.sponge.exception;

import lombok.Getter;

@Getter
public class ResponseError {

    private int resCode;
    private String resMsg;

    public ResponseError(int resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }
}
