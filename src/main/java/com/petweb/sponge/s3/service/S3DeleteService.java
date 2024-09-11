package com.petweb.sponge.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3DeleteService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    /**
     * 동영상,이미지들 삭제
     * @param fileUrls
     */
    public void deleteFiles(List<String> fileUrls) {
        for (String fileUrl : fileUrls) {
            amazonS3.deleteObject(bucket, fileUrl);
        }
    }

    /**
     * 이미지 삭제
     * @param imgUrl
     */
    public void deleteImage(String imgUrl) {
        amazonS3.deleteObject(bucket, imgUrl);
    }
}
