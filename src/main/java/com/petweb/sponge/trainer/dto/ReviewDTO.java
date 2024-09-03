package com.petweb.sponge.trainer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDTO {

    private Long trainerId;
    private int score;
    private String content;
}
