package com.petweb.sponge.post.repository.answer;

import com.petweb.sponge.post.domain.answer.Answer;

import java.util.List;

public interface AnswerRepositoryCustom {
    List<Answer> findAllAnswerWithTrainer(Long problemPostId);
    Answer findAnswer(Long answerId);

    void deleteAnswer(Long answerId, Long loginId);

}
