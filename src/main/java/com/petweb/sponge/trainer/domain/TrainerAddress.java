package com.petweb.sponge.trainer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "trainer_address")
public class TrainerAddress {

    @Id
    @GeneratedValue
    private Long id;
    private String city;
    private String town;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Builder
    public TrainerAddress(String city, String town, Trainer trainer) {
        this.city = city;
        this.town = town;
        this.trainer = trainer;
    }
}
