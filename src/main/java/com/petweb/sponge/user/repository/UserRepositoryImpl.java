package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.QUser;
import com.petweb.sponge.user.domain.QUserAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.petweb.sponge.user.domain.QUser.*;
import static com.petweb.sponge.user.domain.QUserAddress.*;

public class UserRepositoryImpl implements UserRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteUser(Long userId) {
        queryFactory
                .delete(userAddress)
                .where(userAddress.user.id.eq(userId))
                .execute();
        queryFactory
                .delete(user)
                .where(user.id.eq(userId))
                .execute();
    }
}
