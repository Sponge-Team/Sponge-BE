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
@Table(name = "history")
@EntityListeners(AuditingEntityListener.class)
public class History {


    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String startDt;
    private String endDt;
    private String description;

    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Builder
    public History(String title, String startDt, String endDt, String description) {
        this.title = title;
        this.startDt = startDt;
        this.endDt = endDt;
        this.description = description;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
