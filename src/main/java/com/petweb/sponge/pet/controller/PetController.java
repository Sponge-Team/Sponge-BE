package com.petweb.sponge.pet.controller;

import com.petweb.sponge.pet.dto.PetDTO;
import com.petweb.sponge.pet.service.PetService;
import com.petweb.sponge.utils.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {

    private final PetService petService;
    private final AuthorizationUtil authorizationUtil;


    /**
     * 반려동물 정보 단건 조회
     * @param petId
     * @return
     */
    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable("petId") Long petId) {
        PetDTO pet = petService.findPet(petId);
        return new ResponseEntity<>(pet,HttpStatus.OK);
    }

    /**
     * 반려동물 등록
     *
     * @param petDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<PetDTO> registerPet(@RequestBody PetDTO petDTO) {
        PetDTO pet = petService.savePet(authorizationUtil.getLoginId(), petDTO);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    /**
     * 반려동물 삭제
     * @param petId
     */
    @DeleteMapping("/{petId}")
    public void removePet(@PathVariable("petId")Long petId) {
        petService.deletePet(petId);
    }


}
