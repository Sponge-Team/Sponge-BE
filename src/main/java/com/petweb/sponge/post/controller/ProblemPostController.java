package com.petweb.sponge.post.controller;

import com.petweb.sponge.post.dto.ProblemPostDTO;
import com.petweb.sponge.post.service.ProblemPostService;
import com.petweb.sponge.utils.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class ProblemPostController {


    private final ProblemPostService problemPostService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 글 작성 저장
     * @param problemPostDTO
     */
    @PostMapping()
    public void writePost(@RequestBody ProblemPostDTO problemPostDTO) {
            problemPostService.savePost(authorizationUtil.getLoginId(),problemPostDTO);
    }

}
