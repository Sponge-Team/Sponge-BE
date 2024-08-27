package com.petweb.sponge.post.repository;

import com.petweb.sponge.post.domain.ProblemPost;

import java.util.List;

public interface ProblemPostRepositoryCustom {


    ProblemPost findPostWithType(Long problemPostId);
    List<ProblemPost> findAllPostByProblemCode(Long problemTypeCode);
    void deletePost(Long problemPostId);

}
