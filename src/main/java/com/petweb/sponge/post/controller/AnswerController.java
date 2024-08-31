package com.petweb.sponge.post.controller;

import com.petweb.sponge.auth.TrainerAuth;
import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.post.dto.answer.*;
import com.petweb.sponge.post.service.AnswerService;
import com.petweb.sponge.utils.AuthorizationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;
    private final AuthorizationUtil authorizationUtil;


    /**
     * 문제행동글에 딸린 훈련사 답변 조회
     *
     * @param problemPostId
     * @return
     */
    @GetMapping
    public ResponseEntity<List<AnswerDetailDTO>> getAllAnswer(@RequestParam Long problemPostId) {
        List<AnswerDetailDTO> answerList = answerService.findAnswerList(problemPostId);
        return new ResponseEntity<>(answerList, HttpStatus.OK);
    }

    /**
     * 훈련사 답변 작성
     * @param answerDTO
     */
    @PostMapping
    @TrainerAuth
    public void writeAnswer(@RequestBody AnswerDTO answerDTO) {
        answerService.saveAnswer(authorizationUtil.getLoginId(), answerDTO);
    }

    /**
     * 훈련사 답변 수정
     *
     * @param answerId
     * @param answerUpdateDTO
     */
    @PatchMapping("/{answerId}")
    @TrainerAuth
    public void modifyAnswer(@PathVariable Long answerId, @RequestBody AnswerUpdateDTO answerUpdateDTO) {
        answerService.updateAnswer(answerId, answerUpdateDTO);
    }

    /**
     * 훈련사 답변 삭제
     *
     * @param answerId
     */
    @DeleteMapping("/{answerId}")
    @TrainerAuth
    public void removeAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId, authorizationUtil.getLoginId());
    }

    /**
     * 훈련사 답변 채택
     * @param adoptAnswerDTO
     */
    @PostMapping("/adopt")
    @UserAuth
    public void registerAdoptAnswer(@RequestBody AdoptAnswerDTO adoptAnswerDTO) {
        answerService.saveAdoptAnswer(adoptAnswerDTO,authorizationUtil.getLoginId());
    }

    /**
     * 훈련사 답변 추천
     * @param answerRecommendDTO
     */
    @PostMapping("/like")
    @UserAuth
    public void updateLikeCount(@RequestBody AnswerRecommendDTO answerRecommendDTO) {
        answerService.updateLikeCount(answerRecommendDTO.getAnswerId(),authorizationUtil.getLoginId());

    }


}
