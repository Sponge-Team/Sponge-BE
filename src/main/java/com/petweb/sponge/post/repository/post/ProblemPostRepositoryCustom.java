package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.ProblemPost;

import java.util.List;

public interface ProblemPostRepositoryCustom {


    ProblemPost findPostWithType(Long problemPostId);
    List<ProblemPost> findAllPostByProblemCode(Long problemTypeCode);
    void deletePost(Long problemPostId);

}
