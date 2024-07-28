package com.petweb.sponge.user.dto;

import lombok.Getter;

@Getter
public class TrainerDTO {


    private Long userId; //유저아이디
    private Long trainerId; //훈련사 아이디
    private String name; //이름
    private String profileImgUrl; //프로필이미지링크
    private String content; //자기소개
    private int years; //연차
    private String history; //이력
    private int city; //광역시,도 코드
    private int town; //구,시

    /**
     * Test 및 DTO 변환을 위한 생성자
     * @param userId
     * @param trainerId
     * @param name
     * @param profileImgUrl
     * @param content
     * @param years
     * @param history
     * @param city
     * @param town
     */
    public TrainerDTO(Long userId, Long trainerId, String name, String profileImgUrl, String content, int years, String history, int city, int town) {
        this.userId = userId;
        this.trainerId = trainerId;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
        this.content = content;
        this.years = years;
        this.history = history;
        this.city = city;
        this.town = town;
    }
}
