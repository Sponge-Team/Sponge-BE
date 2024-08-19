package com.petweb.sponge.utils;

import lombok.Getter;

@Getter
public enum ProblemTypeUtil {


    SEPARATION(100L, "분리 불안"),
    SOCIAL(200L, "사회성"),

    BARKING(300L, "요구성 짖음"),

    BITE(400L, "입질/경계");


    private final Long code;
    private final String description;

    ProblemTypeUtil(Long code, String description) {
        this.code = code;
        this.description = description;
    }

}
