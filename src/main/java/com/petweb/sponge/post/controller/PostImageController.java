package com.petweb.sponge.post.controller;

import com.petweb.sponge.s3image.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/image")
public class PostImageController {

    private final S3ImageService s3ImageService;

    /**
     * 게시글 이미지 업로드
     * @param multipartFiles
     * @return
     */
    @PostMapping
    public ResponseEntity<List<String>> uploadPostImg(@RequestParam List<MultipartFile> multipartFiles) {
        List<String> saveFiles = s3ImageService.saveImages(multipartFiles, "post");
        return new ResponseEntity<>(saveFiles, HttpStatus.OK);
    }
}
