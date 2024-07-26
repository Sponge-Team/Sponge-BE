package com.petweb.sponge.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue
    @Column(name = "trainer_id")
    private Long id;
    private String content;
    private int years;
    private String history;
    private int city;
    private int town;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Trainer(String content, int years, String history, int city, int town, User user) {
        this.content = content;
        this.years = years;
        this.history = history;
        this.city = city;
        this.town = town;
        this.user = user;
    }



}
