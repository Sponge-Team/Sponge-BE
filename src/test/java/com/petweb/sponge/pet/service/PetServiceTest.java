package com.petweb.sponge.pet.service;

import com.petweb.sponge.pet.domain.Pet;
import com.petweb.sponge.pet.dto.PetDTO;
import com.petweb.sponge.pet.repository.PetRepository;
import com.petweb.sponge.user.domain.User;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;
    @Mock
    private UserRepository userRepository;
    private User findUser;
    private Pet pet;
    private PetDTO petDTO;
    private Long loginId = 1L;

    @BeforeEach
    void setUp() {
        findUser = User.builder()
                .email("test")
                .name("test")
                .build();
        ReflectionTestUtils.setField(findUser, "id", 1L);
        pet = Pet.builder()
                .name("몽미")
                .breed("골든 리트리버")
                .gender(Gender.NEUTERED_MALE.getCode())
                .age(30)
                .weight(4.5f)
                .user(findUser)
                .build();
        petDTO = PetDTO.builder()
                .petName("몽미")
                .breed("골든 리트리버")
                .gender(Gender.NEUTERED_MALE.getCode())
                .age(30)
                .weight(4.5f)
                .build();
    }
    @Test
    @DisplayName("펫 정보 단건 조회")
    void findPet () {
        // Given
        given(petRepository.findById(anyLong())).willReturn(Optional.of(pet));

        // When
        PetDTO findPet = petService.findPet(1L);

        // Then
        assertThat(findPet).isNotNull();
        assertThat(findPet.getPetName()).isEqualTo(petDTO.getPetName());

    }
    @Test
    @DisplayName("펫 정보 저장")
    void savePet() {
        // Given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(findUser));
        given(petRepository.save(any(Pet.class))).willReturn(pet);

        // When
        petService.savePet(loginId, petDTO);

        //Then
        assertThat(pet.getName()).isEqualTo(petDTO.getPetName());

    }
}