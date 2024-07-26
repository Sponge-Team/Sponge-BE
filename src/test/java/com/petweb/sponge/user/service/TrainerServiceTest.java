package com.petweb.sponge.user.service;

import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.TrainerDTO;
import com.petweb.sponge.user.repository.TrainerRepository;
import com.petweb.sponge.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @InjectMocks
    private TrainerService trainerService;

    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private UserRepository userRepository;


    private TrainerDTO trainerDTO;
    private User user;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainerDTO = new TrainerDTO(1l, null, "test", null, "test", 3, "test", 200, 300);
        user = new User(1l,"test@naver.com", "test@naver.com", null);
        trainer = new Trainer("Content", 5, "History", 200, 300, user);
    }

    @Test
    @DisplayName("훈련사 저장")
    void saveTrainer() {
        // Given
        given(userRepository.findById(trainerDTO.getUserId())).willReturn(Optional.of(user));
        given(trainerRepository.save(any(Trainer.class))).willReturn(trainer);

        // When
        TrainerDTO savedTrainerDTO = trainerService.saveTrainer(trainerDTO);

        // Then
        assertThat(savedTrainerDTO).isNotNull();
        assertThat(savedTrainerDTO.getUserId()).isEqualTo(trainerDTO.getUserId());
        then(trainerRepository).should(times(1)).save(any(Trainer.class));
    }

    @Test
    @DisplayName("userId로 db에 유저가 발견되지 않았을때")
    void userNotFound() {
        // Given
        given(userRepository.findById(trainerDTO.getUserId())).willReturn(Optional.empty());

        // When //then
        assertThatThrownBy(() -> trainerService.saveTrainer(trainerDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Not Found User");
        then(userRepository).should(times(1)).findById(trainerDTO.getUserId());
        then(trainerRepository).shouldHaveNoInteractions();
    }


}