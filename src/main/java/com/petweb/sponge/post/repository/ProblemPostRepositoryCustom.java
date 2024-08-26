package com.petweb.sponge.post.repository;

import com.petweb.sponge.post.domain.ProblemPost;

public interface ProblemPostRepositoryCustom {


    ProblemPost findPostWithType(Long problemPostId);
    void deletePost(Long problemPostId);

}
