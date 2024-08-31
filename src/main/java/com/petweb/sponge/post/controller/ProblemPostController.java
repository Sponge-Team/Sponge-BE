package com.petweb.sponge.post.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.post.dto.post.PostDetailDTO;
import com.petweb.sponge.post.dto.post.PostRecommendDTO;
import com.petweb.sponge.post.dto.post.ProblemPostDTO;
import com.petweb.sponge.post.dto.post.ProblemPostListDTO;
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
    public ResponseEntity<PostDetailDTO> getPost(@PathVariable("problemPostId") Long problemPostId) {
        PostDetailDTO problemPost = problemPostService.findPost(problemPostId);
        return new ResponseEntity<>(problemPost, HttpStatus.OK);
    }

    /**
     * 카테고리별 글 전체조회
     * TODO 전체버튼을 클릭하면 어떻게 전체를 조회할지
     * @param problemTypeCode
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ProblemPostListDTO>> getAllPost(@RequestParam("problemTypeCode") Long problemTypeCode) {
        List<ProblemPostListDTO> problemPostList = problemPostService.findPostList(problemTypeCode);
        return new ResponseEntity<>(problemPostList, HttpStatus.OK);
    }

    /**
     * 글 작성 저장
     *
     * @param problemPostDTO
     */
    @PostMapping
    @UserAuth
    public void writePost(@RequestBody ProblemPostDTO problemPostDTO) {
        problemPostService.savePost(authorizationUtil.getLoginId(), problemPostDTO);
    }

    /**
     * 글 삭제
     *
     * @param problemPostId
     */
    @DeleteMapping("/{problemPostId}")
    @UserAuth
    public void removePost(@PathVariable("problemPostId") Long problemPostId) {
        problemPostService.deletePost(problemPostId);
    }


    /**
     * 추천수 업데이트
     *
     * @param postRecommendDto
     */
    @PostMapping("/like")
    @UserAuth
    public void updateLikeCount(@RequestBody PostRecommendDTO postRecommendDto) {
        problemPostService.updateLikeCount(postRecommendDto.getProblemPostId(), authorizationUtil.getLoginId());
    }
}