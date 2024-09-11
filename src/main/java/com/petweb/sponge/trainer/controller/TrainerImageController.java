package com.petweb.sponge.trainer.controller;

import com.petweb.sponge.auth.TrainerAuth;
import com.petweb.sponge.s3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/image")
public class TrainerImageController {

    private final S3UploadService s3UploadService;

    /**
     * 훈련사 이미지 저장
     * @param multipartFile
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @TrainerAuth
    public ResponseEntity<String> uploadTrainerImg(@RequestPart MultipartFile multipartFile) {
        String saveFile = s3UploadService.saveImage(multipartFile, "profile");
        return new ResponseEntity<>(saveFile, HttpStatus.OK);
    }
}
