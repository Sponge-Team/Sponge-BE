package com.petweb.sponge.user.repository;

import com.petweb.sponge.TestConfig;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.UserDTO;
import com.petweb.sponge.utils.Gender;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email("test")
                .name("test")
                .build();
        UserDTO userDTO = UserDTO
                .builder()
                .name("김유저")
                .gender(Gender.FEMALE.getCode())
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
        user.settingUser(userDTO);
        userRepository.save(user);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("유저, 주소 fetchJoin 조회")
    void findUserWithAddress() {

        // When
        User user = userRepository.findUserWithAddress(1L);

        // Then
        assertThat(user.getUserAddresses().size()).isEqualTo(2);
    }

}