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

    // JPA 더티체킹 변경감지를 위한 setter 메소드들
    public void setContent(String content) {
        this.content = content;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public void setTown(int town) {
        this.town = town;
    }
}
