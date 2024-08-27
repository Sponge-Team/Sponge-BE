package com.petweb.sponge.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostDetailDto {

    private Long userId;
    private Long problemPostId;

    private String title;
    private String content;
    private String duration;
    private int likeCount;
    private String petName;
    private String breed;
    private int gender; //반려견 성별
    private int age; //반려견 나이
    private float weight; //반려견 몸무게
    private String createdAt; //생성시간
    private List<Long> problemTypeList;
    private List<String> hasTagList;
    private List<String> imageUrlList;
}
