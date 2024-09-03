package com.petweb.sponge.trainer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDetailDTO {

    private Long userId;
    private String userName;
    private String userProfileImgUrl;
    private int score;
    private String content;
}
