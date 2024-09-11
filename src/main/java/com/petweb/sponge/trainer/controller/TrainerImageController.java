package com.petweb.sponge.trainer.controller;

import com.petweb.sponge.auth.TrainerAuth;
import com.petweb.sponge.exception.error.LoginIdError;
import com.petweb.sponge.s3.dto.ImageDTO;
import com.petweb.sponge.s3.service.S3DeleteService;
import com.petweb.sponge.s3.service.S3UploadService;
import com.petweb.sponge.trainer.service.TrainerService;
import com.petweb.sponge.utils.AuthorizationUtil;
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
    private final S3DeleteService s3DeleteService;
    private final TrainerService trainerService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 훈련사 이미지 저장
     *
     * @param multipartFile
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @TrainerAuth
    public ResponseEntity<String> uploadTrainerImg(@RequestPart MultipartFile multipartFile) {
        String saveFile = s3UploadService.saveImage(multipartFile, "profile");
        return new ResponseEntity<>(saveFile, HttpStatus.OK);
    }

    /**
     * 훈련사 이미지 삭제
     * @param trainerId
     * @param imageDTO
     */
    @DeleteMapping("/{trainerId}")
    @TrainerAuth
    public void deleteTrainerImg(@PathVariable("trainerId") Long trainerId, @RequestBody ImageDTO imageDTO) {
        if (authorizationUtil.getLoginId().equals(trainerId)) {
            // S3에서 삭제
            s3DeleteService.deleteImage(imageDTO.getImgUrl());
            //훈련사 이미지 링크 삭제
            trainerService.deleteTrainerImg(trainerId);
        } else {
            throw new LoginIdError();
        }

    }
}
