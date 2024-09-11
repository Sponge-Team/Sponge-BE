package com.petweb.sponge.post.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.s3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/file")
public class PostFileController {

    private final S3UploadService s3UploadService;

    /**
     * 게시글 이미지 업로드
     * @param multipartFiles
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @UserAuth
    public ResponseEntity<List<String>> uploadPostFiles(@RequestPart List<MultipartFile> multipartFiles) {
        List<String> saveFiles = s3UploadService.saveFiles(multipartFiles, "post");
        return new ResponseEntity<>(saveFiles, HttpStatus.OK);
    }
}
