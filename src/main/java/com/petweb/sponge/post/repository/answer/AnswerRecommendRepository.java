package com.petweb.sponge.post.repository.answer;

import com.petweb.sponge.post.domain.answer.AnswerRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnswerRecommendRepository extends JpaRepository<AnswerRecommend,Long> {

    @Query("SELECT ar FROM AnswerRecommend ar WHERE ar.answer.id = :answerId AND ar.user.id = :userId")
    Optional<AnswerRecommend> findRecommend(@Param("answerId") Long answerId, @Param("userId") Long userId);
}
