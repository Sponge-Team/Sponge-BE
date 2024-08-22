package com.petweb.sponge.post.repository;

import com.petweb.sponge.post.domain.ProblemPost;
import com.petweb.sponge.post.domain.ProblemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemPostRepository extends JpaRepository<ProblemPost,Long> {



}
