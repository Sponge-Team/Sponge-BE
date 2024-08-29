package com.petweb.sponge.post.domain.post;

import com.petweb.sponge.post.domain.ProblemType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_category")
public class PostCategory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_post_id")
    private ProblemPost problemPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_type_id")
    private ProblemType problemType;
    @Builder
    public PostCategory(ProblemPost problemPost, ProblemType problemType) {
        this.problemPost = problemPost;
        this.problemType = problemType;
    }
}
