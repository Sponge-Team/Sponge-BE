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


    /**
     * Entity 변환을 위한 생성자
     * @param content
     * @param years
     * @param history
     * @param city
     * @param town
     * @param user
     */
    public Trainer(String content, int years, String history, int city, int town, User user) {
        this.content = content;
        this.years = years;
        this.history = history;
        this.city = city;
        this.town = town;
        this.user = user;
    }

    /**
     * Test를 위한 생성자
     * @param id
     * @param content
     * @param years
     * @param history
     * @param city
     * @param town
     * @param user
     */
    public Trainer(Long id, String content, int years, String history, int city, int town, User user) {
        this.id = id;
        this.content = content;
        this.years = years;
        this.history = history;
        this.city = city;
        this.town = town;
        this.user = user;
    }

    /**
     * 훈련사 정보 수정 메소드
     * @param content
     * @param years
     * @param history
     * @param city
     * @param town
     */
    public void changeTrainerInfo(String content, int years, String history, int city, int town) {
        this.content = content;
        this.years = years;
        this.history = history;
        this.city = city;
        this.town = town;
    }
}
