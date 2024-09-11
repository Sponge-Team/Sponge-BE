package com.petweb.sponge.post.service;

import com.petweb.sponge.post.repository.post.PostFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFileService {
    private final PostFileRepository postFileRepository;

    @Transactional
    public void deletePostFile(List<String> fileUrlList) {
        postFileRepository.deleteByFiles(fileUrlList);
    }
}
