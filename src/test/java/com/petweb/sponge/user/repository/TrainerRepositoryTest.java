package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
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
        user = new User("test1", "test1", null);
        userRepository.save(user);
        trainer = new Trainer("testContent", 1, "testHistory", 100, 200, user);
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