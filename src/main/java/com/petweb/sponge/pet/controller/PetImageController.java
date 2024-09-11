package com.petweb.sponge.pet.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.pet.service.PetService;
import com.petweb.sponge.s3.dto.ImageDTO;
import com.petweb.sponge.s3.service.S3DeleteService;
import com.petweb.sponge.s3.service.S3UploadService;
import com.petweb.sponge.utils.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet/image")
public class PetImageController {
    private final S3UploadService s3UploadService;
    private final S3DeleteService s3DeleteService;
    private final PetService petService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 반려견 이미지 저장
     *
     * @param multipartFile
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @UserAuth
    public ResponseEntity<String> uploadPetImg(@RequestPart MultipartFile multipartFile) {
        String saveFile = s3UploadService.saveImage(multipartFile, "profile");
        return new ResponseEntity<>(saveFile, HttpStatus.OK);
    }

    /**
     * 반려견 이미지 삭제
     * @param petId
     * @param imageDTO
     */
    @DeleteMapping("/{petId}")
    @UserAuth
    public void deletePetImg(@PathVariable("petId") Long petId, @RequestBody ImageDTO imageDTO) {
        // S3에서 삭제
        s3DeleteService.deleteImage(imageDTO.getImgUrl());
        // 펫 이미지 링크 삭제
        petService.deletePetImg(authorizationUtil.getLoginId(),petId);
    }
}
