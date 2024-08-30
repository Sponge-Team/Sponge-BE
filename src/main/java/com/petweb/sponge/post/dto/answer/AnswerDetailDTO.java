package com.petweb.sponge.post.dto.answer;

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

    private boolean postWriter; // 문제행동 글을 작성한 유저인지 아닌지를 구분


}
