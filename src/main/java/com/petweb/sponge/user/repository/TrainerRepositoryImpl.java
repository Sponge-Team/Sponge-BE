package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.QTrainer;
import com.petweb.sponge.user.domain.QUser;
import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.petweb.sponge.user.domain.QTrainer.*;
import static com.petweb.sponge.user.domain.QUser.*;

public class TrainerRepositoryImpl implements TrainerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public TrainerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Trainer findByTrainerId(Long trainerId) {
        return queryFactory
                .selectFrom(trainer)
                .join(trainer.user,user).fetchJoin()
                .where(trainer.id.eq(trainerId))
                .fetchOne();
    }
}
