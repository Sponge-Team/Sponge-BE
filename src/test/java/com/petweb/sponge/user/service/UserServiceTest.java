package com.petweb.sponge.user.service;

import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.UserDTO;
import com.petweb.sponge.user.repository.UserRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    private User findUser;
    private User user;
    private UserDTO userDTO;
    private Long loginId= 1L;

    @BeforeEach
    void setUp() {
        findUser = User.builder()
                .email("test")
                .name("test")
                .build();
        ReflectionTestUtils.setField(findUser, "id", 1L);
        userDTO = UserDTO.builder()
                .userName("김유저")
                .gender(Gender.FEMALE.getCode())
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
        user = findUser.settingUser(userDTO);
    }

    @Test
    @DisplayName("유저 정보 단건조회")
    void findUser() {
        // Given
        given(userRepository.findUserWithAddress(anyLong())).willReturn(Optional.of(user));

        // When
//        UserDTO findUser = userService.findUser(1L);

        // Then
        assertThat(findUser).isNotNull();
        assertThat(loginId).isEqualTo(user.getId());
    }
    @Test
    @DisplayName("유저 정보 저장")
    void saveUser() {
        //Given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(findUser));
        given(userRepository.save(any(User.class))).willReturn(user);

        //When
        userService.saveUser(loginId, userDTO);

        //Then
        assertThat(loginId).isEqualTo(user.getId());

    }

    @Test
    @DisplayName("유저 찾기 실패")
    public void saveUserNotFound() {
        // Given
        given(userRepository.findById(loginId)).willReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.saveUser(loginId, userDTO);
        });

        assertEquals("NO Found USER", exception.getMessage());
    }
}