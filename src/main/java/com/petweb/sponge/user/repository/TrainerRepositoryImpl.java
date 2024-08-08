package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.QAddress;
import com.petweb.sponge.user.domain.QHistory;
import com.petweb.sponge.user.domain.QTrainer;
import com.petweb.sponge.user.domain.Trainer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.petweb.sponge.user.domain.QAddress.*;
import static com.petweb.sponge.user.domain.QHistory.*;
import static com.petweb.sponge.user.domain.QTrainer.*;

public class TrainerRepositoryImpl implements TrainerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public TrainerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public void deleteTrainer(Long trainerId) {
        queryFactory
                .delete(history)
                .where(history.trainer.id.eq(trainerId))
                .execute();
        queryFactory
                .delete(address)
                .where(address.trainer.id.eq(trainerId))
                .execute();
        queryFactory
                .delete(trainer)
                .where(trainer.id.eq(trainerId))
                .execute();
    }
}
