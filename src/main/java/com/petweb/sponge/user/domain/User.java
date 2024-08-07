package com.petweb.sponge.user.domain;

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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private int gender;
    private String profileImgUrl;
    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;

    @Builder
    public User(String email, String name, int gender, String profileImgUrl) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.profileImgUrl = profileImgUrl;
    }

    public User changeUserInfo(String name, int gender, String profileImgUrl) {
        this.name = name;
        this.gender = gender;
        this.profileImgUrl = profileImgUrl;
        return this;
    }
}
