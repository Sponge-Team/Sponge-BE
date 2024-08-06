package com.petweb.sponge.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private int city;
    private int town;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Builder
    public Address(int city, int town) {
        this.city = city;
        this.town = town;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
