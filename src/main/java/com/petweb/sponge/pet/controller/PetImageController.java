package com.petweb.sponge.pet.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.s3.service.S3UploadService;
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

    /**
     * 반려견 이미지 저장
     * @param multipartFile
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @UserAuth
    public ResponseEntity<String> uploadPetImg(@RequestPart MultipartFile multipartFile) {
        String saveFile = s3UploadService.saveImage(multipartFile, "profile");
        return new ResponseEntity<>(saveFile, HttpStatus.OK);
    }
}
