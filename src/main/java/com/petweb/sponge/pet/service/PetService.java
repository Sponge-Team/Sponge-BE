package com.petweb.sponge.pet.service;

import com.petweb.sponge.pet.domain.Pet;
import com.petweb.sponge.pet.dto.PetDTO;
import com.petweb.sponge.pet.repository.PetRepository;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    /**
     * 펫 정보 조회
     * @param petId
     * @return
     */
    public PetDTO findPet(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new RuntimeException("NO Found Pet"));

        return toDto(pet);
    }
    /**
     * 펫 정보 저장
     * @param loginId
     * @param petDTO
     * @return
     */
    @Transactional
    public PetDTO savePet(Long loginId, PetDTO petDTO) {
        //현재 로그인 유저 정보 가져오기
        User user = userRepository.findById(loginId).orElseThrow(
                () -> new RuntimeException("NO Found USER"));
        Pet pet = Pet.builder()
                .name(petDTO.getName())
                .breed(petDTO.getBreed())
                .gender(petDTO.getGender())
                .age(petDTO.getAge())
                .weight(petDTO.getWeight())
                .user(user)
                .build();
        //반려견 저장
        Pet savedPet = petRepository.save(pet);
        return toDto(savedPet);
    }

    /**
     * 펫 정보 삭제
     * @param petId
     */
    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }

    private PetDTO toDto(Pet pet) {
        return PetDTO.builder()
                .name(pet.getName())
                .breed(pet.getBreed())
                .gender(pet.getGender())
                .age(pet.getAge())
                .weight(pet.getWeight())
                .build();
    }



}
