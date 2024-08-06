package com.petweb.sponge.user.service;

import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.AddressDTO;
import com.petweb.sponge.user.dto.HistoryDTO;
import com.petweb.sponge.user.dto.TrainerDTO;
import com.petweb.sponge.user.dto.TrainerId;
import com.petweb.sponge.user.repository.TrainerRepository;
import com.petweb.sponge.user.repository.UserRepository;
import com.petweb.sponge.utils.Gender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
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
    private Trainer trainer;
    private User user;

    @BeforeEach
    void setUp() {
        trainerDTO = TrainerDTO.builder()
                .userId(1L)
                .name("강훈련사")
                .gender(Gender.MALE.getCode())
                .profileImgUrl(null)
                .content("자기소개")
                .years(3)
                .historyList(Collections.singletonList(
                        HistoryDTO.builder()
                                .title("경력 제목")
                                .startDt("201911")
                                .endDt("202011")
                                .description("설명")
                                .build()
                ))
                .addressList(Arrays.asList(
                        AddressDTO.builder()
                                .city(200)
                                .town(300)
                                .build(),
                        AddressDTO.builder()
                                .city(400)
                                .town(500)
                                .build()
                ))
                .build();

        user = User.builder()
                .email("test@naver.com")
                .build();
        trainer = Trainer.createTrainer(trainerDTO, user);
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(trainer, "id", 1L);

    }

//    @Test
//    @DisplayName("훈련사 정보 단건 조회")
//    void findTrainer() {
//        // Given
//        given(trainerRepository.findByTrainerId(anyLong())).willReturn(trainer);
//
//        // When
//        TrainerDTO findTrainer = trainerService.findTrainer(1l);
//
//        // Then
//        assertThat(findTrainer).isNotNull();
//        assertThat(findTrainer.getTrainerId()).isEqualTo(trainer.getId());
//        assertThat(findTrainer.getUserId()).isEqualTo(trainer.getUser().getId());
//    }

    @Test
    @DisplayName("훈련사 정보 저장")
    void saveTrainer() {
        // Given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(trainerRepository.save(any(Trainer.class))).willReturn(trainer);

        // When
        TrainerId trainerId = trainerService.saveTrainer(trainerDTO);

        // Then
        assertEquals(1L, trainerId.getTrainerId());
        assertEquals(1L, trainerId.getUserId());
    }

    @Test
    @DisplayName("userId로 db에 유저가 발견되지 않았을때")
    void userNotFound() {
        // Given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // When // Then
        assertThatThrownBy(() -> trainerService.saveTrainer(trainerDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Not Found User");
    }

    //    @Test
//    @DisplayName("훈련사 정보 수정")
//    void updateTrainer() {
//        // Given
//        given(trainerRepository.findByTrainerId(anyLong())).willReturn(trainer);
//        TrainerDTO updateDto = new TrainerDTO(trainer.getUser().getId(), trainer.getId(), "update", 1,"testImgUrl", "update content", 10, "update test", 100, 200);
//        trainer.changeTrainerInfo(updateDto.getContent(), updateDto.getYears(), updateDto.getHistory(), updateDto.getCity(), updateDto.getTown());
//        user.changeUserInfo(updateDto.getName(), Gender.MALE.getCode(),updateDto.getProfileImgUrl());
//
//        // When
//        trainerService.updateTrainer(trainer.getId(), updateDto);
//
//    }
//
    @Test
    @DisplayName("훈련사 정보 삭제")
    void deleteTrainer() {

        // Given
        given(trainerRepository.findByTrainerId(anyLong())).willReturn(trainer);
        willDoNothing().given(trainerRepository).deleteById(anyLong());
        willDoNothing().given(userRepository).deleteById(anyLong());

        // When
        trainerService.deleteTrainer(1L);

        // Then

    }

}