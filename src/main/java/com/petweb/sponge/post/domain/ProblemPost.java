package com.petweb.sponge.post.domain;

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
import java.util.function.Function;

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
    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Pet pet;

    @OneToMany(mappedBy = "problemPost", cascade = CascadeType.ALL)
    private List<PostCategory> postCategories = new ArrayList<>();

    @OneToMany(mappedBy = "problemPost", cascade = CascadeType.ALL)
    private List<Tag> tags= new ArrayList<>();

    @OneToMany(mappedBy = "problemPost", cascade = CascadeType.ALL)
    private List<PostImage> postImages= new ArrayList<>();


    @Builder
    public ProblemPost(String title, String content, String duration, int likeCount,User user, Pet pet) {
        this.title = title;
        this.content = content;
        this.duration = duration;
        this.likeCount = likeCount;
        this.user = user;
        this.pet = pet;
    }
}
