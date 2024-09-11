package com.petweb.sponge.post.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post_file")
public class PostFile {
    @Id
    @GeneratedValue
    private Long id;
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_post_id")
    private ProblemPost problemPost;

    @Builder
    public PostFile(String fileUrl, ProblemPost problemPost) {
        this.fileUrl = fileUrl;
        this.problemPost = problemPost;
    }
}
