package com.petweb.sponge.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    private String hashtag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_post_id")
    private ProblemPost problemPost;

    @Builder
    public Tag(String hashtag, ProblemPost problemPost) {
        this.hashtag = hashtag;
        this.problemPost = problemPost;
    }
}
