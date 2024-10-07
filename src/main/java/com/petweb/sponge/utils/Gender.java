package com.petweb.sponge.utils;

import lombok.Getter;

@Getter
public enum Gender {

    MALE(1, "남자"),
    NEUTERED_MALE(2, "남자(중성화)"),
    FEMALE(3,"여자"),
    NEUTERED_FEMALE(4,"여자(중성화)");


    private final int code;
    private final String description;


    Gender(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
