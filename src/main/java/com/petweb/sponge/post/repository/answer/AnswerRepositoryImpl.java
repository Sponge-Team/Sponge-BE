package com.petweb.sponge.post.repository.answer;

import com.petweb.sponge.post.domain.answer.Answer;
import com.petweb.sponge.post.domain.answer.QAnswerRecommend;
import com.petweb.sponge.post.domain.post.QProblemPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.petweb.sponge.post.domain.answer.QAdoptAnswer.*;
import static com.petweb.sponge.post.domain.answer.QAnswer.*;
import static com.petweb.sponge.post.domain.answer.QAnswerRecommend.*;
import static com.petweb.sponge.post.domain.post.QProblemPost.*;
import static com.petweb.sponge.trainer.domain.QTrainer.*;

public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AnswerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Answer> findAllAnswerWithTrainer(Long problemPostId) {
        List<Answer> answerList = queryFactory
                .selectFrom(answer)
                .leftJoin(answer.trainer, trainer).fetchJoin()
                .leftJoin(answer.problemPost,problemPost).fetchJoin()
                .leftJoin(answer.adoptAnswer, adoptAnswer).fetchJoin()
                .where(answer.problemPost.id.eq(problemPostId))
                .fetch();
        if (answerList.isEmpty()) {
            answerList = new ArrayList<>();
        }
        return answerList;
    }

    @Override
    public Answer findAnswer(Long answerId) {
        return queryFactory
                .selectFrom(answer)
                .leftJoin(answer.trainer,trainer).fetchJoin()
                .leftJoin(answer.problemPost,problemPost).fetchJoin()
                .where(answer.id.eq(answerId))
                .fetchOne();

    }

    @Override
    public void deleteAnswer(Long answerId, Long loginId) {
        queryFactory
                .delete(adoptAnswer)
                .where(adoptAnswer.answer.id.eq(answerId)
                        .and(adoptAnswer.trainer.id.eq(loginId)))
                .execute();
        queryFactory
                .delete(answerRecommend)
                .where(answerRecommend.answer.id.eq(answerId))
                .execute();
        queryFactory
                .delete(answer)
                .where(answer.id.eq(answerId))
                .execute();
    }
}
