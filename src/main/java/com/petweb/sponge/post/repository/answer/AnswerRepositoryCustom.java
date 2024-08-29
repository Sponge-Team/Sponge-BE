package com.petweb.sponge.post.repository.answer;

import com.petweb.sponge.post.domain.answer.Answer;

import java.util.List;

public interface AnswerRepositoryCustom {
    List<Answer> findAnswerWithTrainer(Long problemPostId);
}
