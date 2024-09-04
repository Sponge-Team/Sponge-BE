package com.petweb.sponge.user.repository;

import com.petweb.sponge.pet.domain.QPet;
import com.petweb.sponge.user.domain.QUser;
import com.petweb.sponge.user.domain.QUserAddress;
import com.petweb.sponge.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.petweb.sponge.pet.domain.QPet.*;
import static com.petweb.sponge.user.domain.QUser.*;
import static com.petweb.sponge.user.domain.QUserAddress.*;

public class UserRepositoryImpl implements UserRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<User> findUserWithAddress(Long userId) {
        User foundUser = queryFactory
                .selectDistinct(user)
                .from(user)
                .leftJoin(user.userAddresses, userAddress).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();
        return Optional.ofNullable(foundUser);
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

    @Override
    public void initUser(Long userId) {
        queryFactory
                .delete(userAddress)
                .where(userAddress.user.id.eq(userId))
                .execute();
    }
}
