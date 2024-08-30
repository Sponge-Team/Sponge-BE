package com.petweb.sponge.post.domain.answer;

import com.petweb.sponge.post.domain.post.ProblemPost;
import com.petweb.sponge.trainer.domain.Trainer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "answer")
@EntityListeners(AuditingEntityListener.class)
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    private String content; // 내용
    private int likeCount; // 추천수
    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_post_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProblemPost problemPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Trainer trainer;

    @OneToOne(mappedBy = "answer", fetch = FetchType.LAZY)
    private AdoptAnswer adoptAnswer;

    // 내용 업데이트
    public void setContent(String content) {
        this.content = content;
    }

    @Builder
    public Answer(String content, ProblemPost problemPost, Trainer trainer) {
        this.content = content;
        this.problemPost = problemPost;
        this.trainer = trainer;
    }
}
