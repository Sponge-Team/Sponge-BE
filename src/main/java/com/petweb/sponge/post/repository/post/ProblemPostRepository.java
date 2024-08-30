package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.ProblemPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemPostRepository extends JpaRepository<ProblemPost,Long>,ProblemPostRepositoryCustom {


}
