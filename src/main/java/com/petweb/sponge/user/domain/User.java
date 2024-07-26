package com.petweb.sponge.user.domain;

import com.petweb.sponge.user.domain.Trainer;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String profileImgUrl;
    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Trainer trainer;

    public User(String email, String name, String profileImgUrl) {
        this.email = email;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
    }

    public User(Long id, String email, String name, String profileImgUrl) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
    }
}
