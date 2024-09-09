package com.petweb.sponge.utils;

import lombok.Getter;

@Getter
public enum ProblemTypeCode {

    ALL(0L,"전체"),
    SEPARATION(100L,"분리불안"),
    BOWEL(200L,"배변"),
    BARK(300L,"짖음"),
    SOCIAL(400L,"사회성"),
    AGGRESSION(500L,"공격성");
    private final Long code;
    private final String description;


    ProblemTypeCode(Long code, String description) {
        this.code = code;
        this.description = description;
    }
}
