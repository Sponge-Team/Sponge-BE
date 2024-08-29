package com.petweb.sponge.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerDetailDTO {

    private Long answerId;
    private Long trainerId;
    private String trainerName; // 훈련사 이름
    private int adopt_count;
    private int chat_count;
    private String content;
    private int likeCount;
    private boolean adoptCheck; // 채택된건지 안된거지 구분


}
