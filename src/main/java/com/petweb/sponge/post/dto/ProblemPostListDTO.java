package com.petweb.sponge.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProblemPostListDTO {

    private Long problemPostId;
    private String title;
    private String content;
    private int likeCount;
    private String createdAt;
    private List<Long> problemTypeList;

}
