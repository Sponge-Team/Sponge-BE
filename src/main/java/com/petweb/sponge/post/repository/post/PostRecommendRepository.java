package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.PostRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRecommendRepository extends JpaRepository<PostRecommend,Long> {

    @Query("SELECT pr FROM PostRecommend pr WHERE pr.problemPost.id = :problemPostId AND pr.user.id = :userId")
    Optional<PostRecommend> findRecommend(@Param("problemPostId") Long problemPostId, @Param("userId") Long userId);
}
