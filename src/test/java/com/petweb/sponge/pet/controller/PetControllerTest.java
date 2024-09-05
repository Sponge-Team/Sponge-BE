package com.petweb.sponge.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petweb.sponge.pet.dto.PetDTO;
import com.petweb.sponge.pet.service.PetService;
import com.petweb.sponge.utils.AuthorizationUtil;
import com.petweb.sponge.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private PetService petService;

    @MockBean
    private AuthorizationUtil authorizationUtil;

    @Autowired
    private ObjectMapper objectMapper;
    private PetDTO petDTO;

    @BeforeEach
    public void setUp() {
        petDTO = PetDTO.builder()
                .petName("몽미")
                .breed("골든 리트리버")
                .gender(Gender.NEUTERED_MALE.getCode())
                .age(30)
                .weight(4.5f)
                .build();
    }

    @Test
    @DisplayName("반려동물 정보 단건 조회")
    @WithMockUser
    void getPet() throws Exception {
        // Given
        given(petService.findPet(anyLong())).willReturn(petDTO);

        // When // Then
        mockMvc.perform(get("/api/pet/{petId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(petDTO)));


    }

    @Test
    @DisplayName("반려동물 정보 저장")
    @WithMockUser
    void registerPet() throws Exception {
        // Given
        given(authorizationUtil.getLoginId()).willReturn(1L);
        willDoNothing().given(petService).savePet(anyLong(),any(PetDTO.class));

        // When // Then
        mockMvc.perform(post("/api/pet").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("반려동물 정보 삭제")
    @WithMockUser
    void removePet() throws Exception {
        // Given
//        willDoNothing().given(petService).deletePet(anyLong(), petId);

        // When // Given
        mockMvc.perform(delete("/api/pet/{petId}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}