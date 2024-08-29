package com.petweb.sponge.post.controller;

import com.petweb.sponge.post.dto.AnswerDTO;
import com.petweb.sponge.post.dto.AnswerDetailDTO;
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
     * TODO 훈련사인지 아닌지 체크 필요
     * @param answerDTO
     */
    @PostMapping
    public void writeAnswer(@RequestBody AnswerDTO answerDTO) {
        answerService.saveAnswer(authorizationUtil.getLoginId(),answerDTO);
    }

}
