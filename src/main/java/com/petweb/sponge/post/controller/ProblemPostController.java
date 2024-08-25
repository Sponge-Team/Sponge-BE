package com.petweb.sponge.post.controller;

import com.petweb.sponge.post.dto.ProblemPostDTO;
import com.petweb.sponge.post.service.ProblemPostService;
import com.petweb.sponge.utils.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class ProblemPostController {


    private final ProblemPostService problemPostService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 글 단건 조회
     * @param problemPostId
     */
    @GetMapping("/{problemPostId}")
    public void getPost(@PathVariable("problemPostId")Long problemPostId) {
        problemPostService.findPost(problemPostId);
    }
    /**
     * 글 작성 저장
     * @param problemPostDTO
     */
    @PostMapping()
    public void writePost(@RequestBody ProblemPostDTO problemPostDTO) {
            problemPostService.savePost(authorizationUtil.getLoginId(),problemPostDTO);
    }

    /**
     * 글 삭제
     * @param problemPostId
     */
    @DeleteMapping("/{problemPostId}")
    public void removePost(@PathVariable("problemPostId")Long problemPostId) {
        problemPostService.deletePost(problemPostId);
    }

}
