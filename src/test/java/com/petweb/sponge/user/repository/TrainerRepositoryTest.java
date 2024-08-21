package com.petweb.sponge.user.repository;

import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.trainer.dto.HistoryDTO;
import com.petweb.sponge.trainer.dto.TrainerDTO;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import com.petweb.sponge.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;

@DataJpaTest
class TrainerRepositoryTest {

    @Autowired
    private TrainerRepository trainerRepository;

    private Trainer trainer;
    private Trainer findTrainer;
    private TrainerDTO trainerDTO;

    @BeforeEach
    void setup() {
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
        trainerRepository.save(trainer);
    }

    @Test //추후수정 예정
    @DisplayName("훈련사 삭제")
    void findByTrainerId() {
        //when
        trainerRepository.deleteTrainer(1L);
        //then

    }

}