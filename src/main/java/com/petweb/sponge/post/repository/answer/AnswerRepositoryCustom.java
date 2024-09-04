package com.petweb.sponge.post.repository.answer;

import com.petweb.sponge.post.domain.answer.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepositoryCustom {
    List<Answer> findAllAnswerWithTrainer(Long problemPostId);
    Optional<Answer> findAnswer(Long answerId);

    void deleteAnswer(Long answerId, Long loginId);

}
