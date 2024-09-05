package com.petweb.sponge.trainer.controller;

import com.petweb.sponge.auth.TrainerAuth;
import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.s3image.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/image")
public class TrainerImageController {

    private final S3ImageService s3ImageService;

    /**
     * 훈련사 이미지 저장
     * @param multipartFile
     * @return
     */
    @PostMapping
    @TrainerAuth
    public ResponseEntity<String> uploadTrainerImg(@RequestParam MultipartFile multipartFile) {
        String saveFile = s3ImageService.saveImage(multipartFile, "profile");
        return new ResponseEntity<>(saveFile, HttpStatus.OK);
    }
}
