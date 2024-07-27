package com.petweb.sponge.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petweb.sponge.user.dto.TrainerDTO;
import com.petweb.sponge.user.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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
    private TrainerDTO savedTrainerDTO;

    @BeforeEach
    void setUp() {
        trainerDTO = new TrainerDTO(1L, null, "test", "", "test", 3, "test", 200, 300);
        savedTrainerDTO = new TrainerDTO(1L, 1L, "test", "", "test", 3, "test", 200, 300);
    }

    @Test
    @DisplayName("훈련사 정보 단건 조회")
    void getTrainer() throws Exception {
        // Given
        given(trainerService.findTrainer(anyLong())).willReturn(savedTrainerDTO);

        // When // Then
        mockMvc.perform(get("/api/trainer/{trainerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(savedTrainerDTO.getUserId()))
                .andExpect(jsonPath("$.trainerId").value(savedTrainerDTO.getTrainerId()))
                .andExpect(jsonPath("$.name").value(savedTrainerDTO.getName()))
                .andExpect(jsonPath("$.profileImgUrl").value(savedTrainerDTO.getProfileImgUrl()))
                .andExpect(jsonPath("$.content").value(savedTrainerDTO.getContent()))
                .andExpect(jsonPath("$.years").value(savedTrainerDTO.getYears()))
                .andExpect(jsonPath("$.history").value(savedTrainerDTO.getHistory()))
                .andExpect(jsonPath("$.city").value(savedTrainerDTO.getCity()))
                .andExpect(jsonPath("$.town").value(savedTrainerDTO.getTown()));
    }

    @Test
    @DisplayName("훈련사 정보 저장")
    void signup() throws Exception {
        // Given
        given(trainerService.saveTrainer(any(TrainerDTO.class))).willReturn(savedTrainerDTO);

        // When // Then
        mockMvc.perform(post("/api/trainer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trainerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(savedTrainerDTO.getUserId()))
                .andExpect(jsonPath("$.trainerId").value(savedTrainerDTO.getTrainerId()))
                .andExpect(jsonPath("$.name").value(savedTrainerDTO.getName()))
                .andExpect(jsonPath("$.profileImgUrl").value(savedTrainerDTO.getProfileImgUrl()))
                .andExpect(jsonPath("$.content").value(savedTrainerDTO.getContent()))
                .andExpect(jsonPath("$.years").value(savedTrainerDTO.getYears()))
                .andExpect(jsonPath("$.history").value(savedTrainerDTO.getHistory()))
                .andExpect(jsonPath("$.city").value(savedTrainerDTO.getCity()))
                .andExpect(jsonPath("$.town").value(savedTrainerDTO.getTown()));
    }

    @Test
    @DisplayName("훈련사 정보 수정")
    void modifyTrainer() throws Exception {
        // Given
        TrainerDTO updateDto = new TrainerDTO(1L,1L, "update", "testImgUrl", "update content", 10, "update test", 100, 200);
        given(trainerService.findTrainer(anyLong())).willReturn(updateDto);

        // When // Then
        mockMvc.perform(put("/api/trainer/{trainerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(updateDto.getUserId()))
                .andExpect(jsonPath("$.trainerId").value(updateDto.getTrainerId()))
                .andExpect(jsonPath("$.name").value(updateDto.getName()))
                .andExpect(jsonPath("$.profileImgUrl").value(updateDto.getProfileImgUrl()))
                .andExpect(jsonPath("$.content").value(updateDto.getContent()))
                .andExpect(jsonPath("$.years").value(updateDto.getYears()))
                .andExpect(jsonPath("$.history").value(updateDto.getHistory()))
                .andExpect(jsonPath("$.city").value(updateDto.getCity()))
                .andExpect(jsonPath("$.town").value(updateDto.getTown()));
    }

    @Test
    @DisplayName("훈련사 정보 삭제")
    void removeTrainer() {

    }
}