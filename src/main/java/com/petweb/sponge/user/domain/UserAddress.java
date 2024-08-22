package com.petweb.sponge.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue
    private Long id;
    private int city;
    private int town;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserAddress(int city, int town, User user) {
        this.city = city;
        this.town = town;
        this.user = user;
    }
}
