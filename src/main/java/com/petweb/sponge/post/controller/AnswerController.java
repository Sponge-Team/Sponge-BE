package com.petweb.sponge.post.controller;

import com.petweb.sponge.post.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;


}
