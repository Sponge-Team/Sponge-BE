package com.petweb.sponge.post.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.post.dto.post.PostDetailDTO;
import com.petweb.sponge.post.dto.post.PostIdDTO;
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
     *
     * @param problemTypeCode
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ProblemPostListDTO>> getAllPost(@RequestParam("problemTypeCode") Long problemTypeCode, @RequestParam("page") int page) {
        List<ProblemPostListDTO> problemPostList = problemPostService.findPostList(problemTypeCode, page);
        return new ResponseEntity<>(problemPostList, HttpStatus.OK);
    }

    /**
     * 검색 기능
     * @param keyword
     * @param page
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProblemPostListDTO>> searchPost(@RequestParam("keyword") String keyword, @RequestParam("page") int page) {
        List<ProblemPostListDTO> problemPostList = problemPostService.searchPost(keyword,page);
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
     * 글 수정
     *
     * @param problemPostId
     * @param problemPostDTO
     */
    @PatchMapping("/{problemPostId}")
    @UserAuth
    public void updatePost(@PathVariable("problemPostId") Long problemPostId, @RequestBody ProblemPostDTO problemPostDTO) {
        problemPostService.updatePost(authorizationUtil.getLoginId(), problemPostDTO);
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
     * @param postIdDto
     */
    @PostMapping("/like")
    @UserAuth
    public void updateLikeCount(@RequestBody PostIdDTO postIdDto) {
        problemPostService.updateLikeCount(postIdDto.getProblemPostId(), authorizationUtil.getLoginId());
    }

    /**
     * 글 북마크 업데이트
     *
     * @param postIdDTO
     */
    @PostMapping("/bookmark")
    @UserAuth
    public void updateBookmark(@RequestBody PostIdDTO postIdDTO) {
        problemPostService.updateBookmark(postIdDTO, authorizationUtil.getLoginId());
    }
}