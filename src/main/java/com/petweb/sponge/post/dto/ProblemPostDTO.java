package com.petweb.sponge.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ProblemPostDTO {

    private Long petId;
    private String title;
    private String content;
    private String duration;
    private List<Long> categoryCodeList;
    private List<String> hasTagList;
    private List<String> imageUrlList;
}
