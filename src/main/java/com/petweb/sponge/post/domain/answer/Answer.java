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
    // 추천수 증가
    public void increaseLikeCount() {
        this.likeCount++;
    }
    // 추천수 감소
    public void decreaseLikeCount() {
        this.likeCount--;
    }

    @Builder
    public Answer(String content, ProblemPost problemPost, Trainer trainer) {
        this.content = content;
        this.problemPost = problemPost;
        this.trainer = trainer;
    }
    // 답변글 작성 아이디와 로그인아이디가 맞는지
    public boolean isWriteTrainer(Long loginId) {
        return getTrainer().getId().equals(loginId);
    }
    // 문젬행동글을 작성한 유저인지 아닌지
    public boolean isPostWriteUser(Long userId) {
        return getProblemPost().getUser().getId().equals(userId);
    }
}
