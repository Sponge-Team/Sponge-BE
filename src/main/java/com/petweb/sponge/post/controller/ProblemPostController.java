package com.petweb.sponge.post.controller;

import com.petweb.sponge.post.dto.PostDetailDto;
import com.petweb.sponge.post.dto.ProblemPostDTO;
import com.petweb.sponge.post.dto.ProblemPostListDTO;
import com.petweb.sponge.post.service.ProblemPostService;
import com.petweb.sponge.utils.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class ProblemPostController {


    private final ProblemPostService problemPostService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 글 단건 조회
     *
     * @param problemPostId
     * @return
     */
    @GetMapping("/{problemPostId}")
    public ResponseEntity<PostDetailDto> getPost(@PathVariable("problemPostId")Long problemPostId) {
        PostDetailDto problemPost = problemPostService.findPost(problemPostId);
        return new ResponseEntity<>(problemPost, HttpStatus.OK);
    }

    /**
     * 카테고리별 글 전체조회
     *
     * @param problemTypeCode
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ProblemPostListDTO>> getAllPost(@RequestParam("problemTypeCode")Long problemTypeCode) {
        List<ProblemPostListDTO> problemPostList = problemPostService.findPostList(problemTypeCode);
        return new ResponseEntity<>(problemPostList,HttpStatus.OK);
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
     *
     * @param problemPostId
     */
    @DeleteMapping("/{problemPostId}")
    public void removePost(@PathVariable("problemPostId")Long problemPostId) {
        problemPostService.deletePost(problemPostId);
    }


}
