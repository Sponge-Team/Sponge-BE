package com.petweb.sponge.post.controller;

import com.petweb.sponge.s3image.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/img")
public class ProblemPostImgController {

    private final S3UploadService s3UploadService;

    /**
     * 게시글 이미지 업로드
     * @param multipartFiles
     */
    @PostMapping
    public void uploadPostImg(@RequestParam List<MultipartFile> multipartFiles) {
        List<String> saveFiles = s3UploadService.saveImages(multipartFiles, "post");
        
    }


}
