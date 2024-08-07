package com.petweb.sponge.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petweb.sponge.user.dto.AddressDTO;
import com.petweb.sponge.user.dto.HistoryDTO;
import com.petweb.sponge.user.dto.TrainerDTO;
import com.petweb.sponge.user.dto.TrainerId;
import com.petweb.sponge.user.service.TrainerService;
import com.petweb.sponge.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainerController.class)
class TrainerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private TrainerService trainerService;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainerDTO trainerDTO;
    private TrainerId trainerId;

    @BeforeEach
    public void setUp() {
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
        trainerId = TrainerId.builder().trainerId(1L).userId(1L).build();
    }
    @Test
    @DisplayName("훈련사 정보 단건 조회")
    void getTrainer() throws Exception {
        // Given
        given(trainerService.findTrainer(anyLong())).willReturn(trainerDTO);

        // When // Then
        mockMvc.perform(get("/api/trainer/{trainerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(trainerDTO)));
    }

    @Test
    @DisplayName("훈련사 정보 저장")
    void signup() throws Exception {
        // Given
        given(trainerService.saveTrainer(any(TrainerDTO.class))).willReturn(trainerId);

        // When // Then
        mockMvc.perform(post("/api/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(trainerId)));
    }

//    @Test
//    @DisplayName("훈련사 정보 수정")
//    void modifyTrainer() throws Exception {
//        // Given
//        TrainerDTO updateDto = new TrainerDTO(1L,1L, "update", 1,"testImgUrl", "update content", 10, "update test", 100, 200);
//        given(trainerService.findTrainer(anyLong())).willReturn(updateDto);
//
//        // When // Then
//        mockMvc.perform(put("/api/trainer/{trainerId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(updateDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(updateDto.getUserId()))
//                .andExpect(jsonPath("$.trainerId").value(updateDto.getTrainerId()))
//                .andExpect(jsonPath("$.name").value(updateDto.getName()))
//                .andExpect(jsonPath("$.profileImgUrl").value(updateDto.getProfileImgUrl()))
//                .andExpect(jsonPath("$.content").value(updateDto.getContent()))
//                .andExpect(jsonPath("$.years").value(updateDto.getYears()))
//                .andExpect(jsonPath("$.history").value(updateDto.getHistory()))
//                .andExpect(jsonPath("$.city").value(updateDto.getCity()))
//                .andExpect(jsonPath("$.town").value(updateDto.getTown()));
//    }
//
    @Test
    @DisplayName("훈련사 정보 삭제")
    void removeTrainer() throws Exception {
        // Given
        willDoNothing().given(trainerService).deleteTrainer(anyLong());

        // When  // Then
        mockMvc.perform(delete("/api/trainer/{trainerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}