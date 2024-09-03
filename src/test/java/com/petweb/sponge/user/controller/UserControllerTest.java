package com.petweb.sponge.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.user.dto.UserDetailDTO;
import com.petweb.sponge.user.service.UserService;
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

import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthorizationUtil authorizationUtil;

    @Autowired
    private ObjectMapper objectMapper;
    private UserDetailDTO userDetailDTO;

    @BeforeEach
    public void setUp() {
        userDetailDTO = UserDetailDTO.builder()
                .name("김유저")
                .gender(Gender.NEUTERED_FEMALE.getCode())
                .phone("010-1111-1111")
                .profileImgUrl(null)
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
    }
    @Test
    @DisplayName("유저 정보 단건조회")
    @WithMockUser
    void getUser() throws Exception {
        // Given
        given(userService.findUser(anyLong())).willReturn(userDetailDTO);

        // When // Then
        mockMvc.perform(get("/api/user/{userId}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDetailDTO)));
    }

    @Test
    @DisplayName("유저 정보 저장")
    @WithMockUser
    void signup() throws Exception {
        // Given
        given(authorizationUtil.getLoginId()).willReturn(1L);
        willDoNothing().given(userService).saveUser(anyLong(),any(UserDetailDTO.class));

        // When // Then
        mockMvc.perform(post("/api/user").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetailDTO)))
                .andExpect(status().isOk());
    }
}