package com.petweb.sponge.post.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.s3.dto.FileListDTO;
import com.petweb.sponge.post.service.PostFileService;
import com.petweb.sponge.s3.service.S3DeleteService;
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
    private final S3DeleteService s3DeleteService;
    private final PostFileService postFileService;

    /**
     * 게시글 이미지,동영상 업로드
     *
     * @param multipartFiles
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @UserAuth
    public ResponseEntity<List<String>> uploadPostFiles(@RequestPart List<MultipartFile> multipartFiles) {
        List<String> saveFiles = s3UploadService.saveFiles(multipartFiles, "post");
        return new ResponseEntity<>(saveFiles, HttpStatus.OK);
    }


    /**
     * 게시글 이미지,동영상 삭제
     * TODO 본인이 적은 글인지 체크 필요
     * @param fileListDTO
     */
    @DeleteMapping
    @UserAuth
    public void deletePostFile(@RequestBody FileListDTO fileListDTO) {
        // S3에서 삭제
        s3DeleteService.deleteFiles(fileListDTO.getFileUrlList());

        // DB에서 삭제
        postFileService.deletePostFile(fileListDTO.getFileUrlList());
    }
}
