package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findUserWithAddress(Long userId);
    void deleteUser(Long userId);
    void initUser(Long userId);
}
