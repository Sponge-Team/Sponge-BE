package com.petweb.sponge.trainer.repository;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.TestConfig;
import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.trainer.dto.HistoryDTO;
import com.petweb.sponge.trainer.dto.TrainerDTO;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.utils.Gender;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class TrainerRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private TrainerRepository trainerRepository;


    @BeforeEach
    void setup() {
        Trainer trainer = new Trainer("test", "test");
        TrainerDTO trainerDTO = TrainerDTO.builder()
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
        trainer.settingTrainer(trainerDTO);
        trainerRepository.save(trainer);
        em.flush();
        em.clear();

    }

    @Test
    @DisplayName("훈련사, 주소 fetchJoin 조회")
    void findTrainerWithAddress() {

        // When
        Trainer trainer = trainerRepository.findTrainerWithAddress(1L).orElseThrow(
                () -> new NotFoundException("NO Found Trainer"));

        // Then
        assertThat(trainer.getTrainerAddresses().size()).isEqualTo(2);

    }


}