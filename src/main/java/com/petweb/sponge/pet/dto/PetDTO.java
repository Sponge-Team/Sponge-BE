package com.petweb.sponge.pet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PetDTO {

    private String name; // 반려견 이름
    private String breed; // 견종
    private int gender; // 성별
    private int age; // 나이
    private float weight; // 몸무게
}
