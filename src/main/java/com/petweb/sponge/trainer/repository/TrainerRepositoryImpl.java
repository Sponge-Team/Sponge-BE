package com.petweb.sponge.trainer.repository;

import com.petweb.sponge.trainer.domain.QTrainer;
import com.petweb.sponge.trainer.domain.QTrainerAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.petweb.sponge.trainer.domain.QHistory.*;
import static com.petweb.sponge.trainer.domain.QTrainer.*;
import static com.petweb.sponge.trainer.domain.QTrainerAddress.*;

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
                .delete(trainerAddress)
                .where(trainerAddress.trainer.id.eq(trainerId))
                .execute();
        queryFactory
                .delete(trainer)
                .where(trainer.id.eq(trainerId))
                .execute();
    }
}
