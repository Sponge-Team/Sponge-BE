package com.petweb.sponge.trainer.service;

import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.trainer.dto.HistoryDTO;
import com.petweb.sponge.trainer.dto.TrainerDTO;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import com.petweb.sponge.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private Trainer findTrainer;
    private TrainerDTO trainerDTO;
    private Trainer trainer;
    private Long loginId=1L;

    @BeforeEach
    void setUp() {
        findTrainer = new Trainer("test","test");
        ReflectionTestUtils.setField(findTrainer, "id", 1L);
        trainerDTO = TrainerDTO.builder()
                .name("강훈련사")
                .gender(Gender.MALE.getCode())
                .phone("010-0000-0000")
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
        trainer = findTrainer.settingTrainer(trainerDTO);

    }

    @Test
    @DisplayName("훈련사 정보 단건 조회")
    void findTrainer() {
        // Given
        given(trainerRepository.findById(anyLong())).willReturn(Optional.of(trainer));

        // When
        TrainerDTO findTrainer = trainerService.findTrainer(1L);

        // Then
        assertThat(findTrainer).isNotNull();
        assertThat(loginId).isEqualTo(trainer.getId());
    }

    @Test
    @DisplayName("훈련사 정보 저장")
    void saveTrainer() {
        // Given
        given(trainerRepository.findById(anyLong())).willReturn(Optional.of(findTrainer));
        given(trainerRepository.save(any(Trainer.class))).willReturn(trainer);

        // When
        trainerService.saveTrainer(loginId, trainerDTO);

        // Then
        assertEquals(loginId, trainer.getId());
    }

    @Test
    @DisplayName("훈련사 찾기 실패")
    public void saveTrainerNotFound() {
        // Given
        given(trainerRepository.findById(loginId)).willReturn(Optional.empty());

        // When // Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainerService.saveTrainer(loginId, trainerDTO);
        });

        assertEquals("NO Found Trainer", exception.getMessage());
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
    @Test //추후수정 예정
    @DisplayName("훈련사 정보 삭제")
    void deleteTrainer() {

        // Given
        given(trainerRepository.findById(anyLong())).willReturn(Optional.of(trainer));
        willDoNothing().given(trainerRepository).deleteTrainer(anyLong());

        // When
        trainerService.deleteTrainer(1L);

        // Then

    }

}