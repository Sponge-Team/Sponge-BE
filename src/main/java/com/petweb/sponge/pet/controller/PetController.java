package com.petweb.sponge.pet.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.pet.dto.PetDTO;
import com.petweb.sponge.pet.service.PetService;
import com.petweb.sponge.utils.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {

    private final PetService petService;
    private final AuthorizationUtil authorizationUtil;


    /**
     * 반려동물 정보 단건 조회
     *
     * @param petId
     * @return
     */
    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable("petId") Long petId) {
        PetDTO pet = petService.findPet(petId);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }


    /**
     * 반려동물 전체 조회
     *
     * @param userId
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<PetDTO>> getAllPet(@RequestParam Long userId) {
        List<PetDTO> petList = petService.findAllPet(userId);
        return new ResponseEntity<>(petList, HttpStatus.OK);
    }

    /**
     * 반려동물 등록
     *
     * @param petDTO
     */
    @PostMapping
    @UserAuth
    public void registerPet(@RequestBody PetDTO petDTO) {
        petService.savePet(authorizationUtil.getLoginId(), petDTO);
    }

    /**
     * 반려동물 삭제
     *
     * @param petId
     */
    @DeleteMapping("/{petId}")
    @UserAuth
    public void removePet(@PathVariable("petId") Long petId) {
        petService.deletePet(petId);
    }


}
