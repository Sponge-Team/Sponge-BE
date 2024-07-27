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
        trainerDTO = new TrainerDTO(1l, null, "test", "", "test", 3, "test", 200, 300);
        user = new User(1l, "test@naver.com", "test@naver.com", "");
        trainer = new Trainer(1l, "Content", 5, "History", 200, 300, user);
    }

    @Test
    @DisplayName("훈련사 정보 단건 조회")
    void findTrainer() {
        // Given
        given(trainerRepository.findByTrainerId(anyLong())).willReturn(trainer);

        // When
        TrainerDTO findTrainer = trainerService.findTrainer(1l);

        // Then
        assertThat(findTrainer).isNotNull();
        assertThat(findTrainer.getTrainerId()).isEqualTo(trainer.getId());
        assertThat(findTrainer.getUserId()).isEqualTo(trainer.getUser().getId());
    }

    @Test
    @DisplayName("훈련사 정보 저장")
    void saveTrainer() {
        // Given
        given(userRepository.findById(trainerDTO.getUserId())).willReturn(Optional.of(user));
        given(trainerRepository.save(any(Trainer.class))).willReturn(trainer);

        // When
        TrainerDTO savedTrainerDTO = trainerService.saveTrainer(trainerDTO);

        // Then
        assertThat(savedTrainerDTO).isNotNull();
        assertThat(savedTrainerDTO.getUserId()).isEqualTo(trainerDTO.getUserId());
    }

    @Test
    @DisplayName("userId로 db에 유저가 발견되지 않았을때")
    void userNotFound() {
        // Given
        given(userRepository.findById(trainerDTO.getUserId())).willReturn(Optional.empty());

        // When // Then
        assertThatThrownBy(() -> trainerService.saveTrainer(trainerDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Not Found User");
        then(userRepository).should(times(1)).findById(trainerDTO.getUserId());
        then(trainerRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("훈련사 정보 수정")
    void updateTrainer() {
        // Given
        given(trainerRepository.findByTrainerId(anyLong())).willReturn(trainer);
        TrainerDTO updateDto = new TrainerDTO(trainer.getUser().getId(), trainer.getId(), "update", "testImgUrl", "update content", 10, "update test", 100, 200);
        trainer.changeTrainerInfo(updateDto.getContent(), updateDto.getYears(), updateDto.getHistory(), updateDto.getCity(), updateDto.getTown());
        user.changeUserInfo(updateDto.getName(), updateDto.getProfileImgUrl());

        // When
        trainerService.updateTrainer(trainer.getId(), updateDto);

    }

    @Test
    @DisplayName("훈련사 정보 삭제")
    void deleteTrainer() {

    }

}