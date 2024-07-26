package com.petweb.sponge.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "Trainers")
public class Trainer {

    @Id
    @GeneratedValue
    @Column(name = "trainer_id")
    private Long id;
    private String content;
    private int years;
    private int city;
    private int town;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
