package com.petweb.sponge.post.domain.post;

import com.petweb.sponge.pet.domain.Pet;
import com.petweb.sponge.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "problem_post")
@EntityListeners(AuditingEntityListener.class)
public class ProblemPost {
    @Id
    @GeneratedValue
    private Long id;
    private String title; // 글제목
    private String content; // 글내용
    private String duration; // 문제행동 지속기간
    private int likeCount; // 추천수
    private int answerCount; // 답변수
    @CreatedDate
    private Timestamp createdAt;
    /**
     * T: Timestamp 대신 ZonedDateTime을 사용하는게 어떨까요?
     * OS, DB의 Timezone에 의존하기 때문에 명시적으로 나타내는게 인식하기 쉬울듯 합니다
     */
    @LastModifiedDate
    private Timestamp modifiedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Pet pet;

    /**
     * 1:N 관계를 조회하려면 애플리케이션 메모리에 적재하고 쿼리 결과를 반환하기 때문에 Batch Size를 꼭 설정하는게 좋습니다
     */
    @OneToMany(mappedBy = "problemPost", cascade = CascadeType.ALL)
    private List<PostCategory> postCategories = new ArrayList<>();

    @OneToMany(mappedBy = "problemPost", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "problemPost", cascade = CascadeType.ALL)
    private List<PostFile> postFiles = new ArrayList<>();

    public void setPostCategories(List<PostCategory> postCategories) {
        this.postCategories = postCategories;
    }

    // 추천수 증가
    public void increaseLikeCount() {
        this.likeCount++;
    }

    // 추천수 감소
    public void decreaseLikeCount() {
        this.likeCount--;
    }

    //답변수 증가
    public void increaseAnswerCount() {
        this.answerCount++;
    }

    //답변수 감소
    public void decreaseAnswerCount() {
        this.answerCount--;
    }

    @Builder
    public ProblemPost(String title, String content, String duration, int likeCount, User user, Pet pet) {
        this.title = title;
        this.content = content;
        this.duration = duration;
        this.likeCount = likeCount;
        this.user = user;
        this.pet = pet;
    }

    //== 게시글 수정 메서드 ==//
    public void updatePost(String title, String content, List<String> imageUrlList, List<String> hasTagList) {
        this.title = title;
        this.content = content;
        //이미지 링크 저장
        imageUrlList.forEach(imageUrl ->
                {
                    PostFile postFile = PostFile.builder()
                            .fileUrl(imageUrl)
                            .problemPost(this)
                            .build();
                    getPostFiles().add(postFile);
                }
        );
        // 카테고리별글 저장
        hasTagList.forEach(hasTag -> {
            Tag tag = Tag.builder()
                    .hashtag(hasTag)
                    .problemPost(this)
                    .build();
            getTags().add(tag);
        });


    }
}
