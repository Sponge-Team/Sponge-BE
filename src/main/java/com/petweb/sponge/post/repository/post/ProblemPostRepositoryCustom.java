package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.ProblemPost;

import java.util.List;

public interface ProblemPostRepositoryCustom {


    ProblemPost findPostWithType(Long problemPostId);
    ProblemPost findPostWithUser(Long problemPostId);
    List<ProblemPost> findAllPostByProblemCode(Long problemTypeCode, int page);
    void deletePost(Long problemPostId);


}
