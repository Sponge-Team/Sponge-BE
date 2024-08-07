package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.utils.Gender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TrainerRepositoryTest {

    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    UserRepository userRepository;

    private User user;
    private Trainer trainer;

    @BeforeEach
    void setup() {
        user = User.builder()
                .email("test@naver.com")
                .name("test")
                .gender(Gender.MALE.getCode())
                .profileImgUrl(null)
                .build();
        userRepository.save(user);
        trainer = Trainer.builder()
                .content("content")
                .years(2)
                .user(user)
                .build();
        trainerRepository.save(trainer);

    }

    @Test
    @DisplayName("훈련사 조회")
    void findByTrainerId() {
        //when
        Trainer findTrainer = trainerRepository.findByTrainerId(1L);
        //then
        assertThat(findTrainer).isSameAs(trainer);
        assertThat(findTrainer.getUser()).isSameAs(user);

    }

}