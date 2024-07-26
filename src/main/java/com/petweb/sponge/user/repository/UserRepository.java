package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
