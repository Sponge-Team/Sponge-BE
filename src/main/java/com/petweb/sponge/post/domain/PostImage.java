package com.petweb.sponge.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_image")
public class PostImage {
    @Id
    @GeneratedValue
    private Long id;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_post_id")
    private ProblemPost problemPost;

    @Builder
    public PostImage(String imageUrl, ProblemPost problemPost) {
        this.imageUrl = imageUrl;
        this.problemPost = problemPost;
    }
}
