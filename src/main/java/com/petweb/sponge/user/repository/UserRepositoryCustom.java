package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.User;

public interface UserRepositoryCustom {

    User findUserWithAddress(Long userId);
    void deleteUser(Long userId);
}
