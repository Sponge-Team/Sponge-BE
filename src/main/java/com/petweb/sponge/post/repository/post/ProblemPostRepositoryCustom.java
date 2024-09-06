package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.ProblemPost;

import java.util.List;
import java.util.Optional;

public interface ProblemPostRepositoryCustom {


    Optional<ProblemPost> findPostWithType(Long problemPostId);
    Optional<ProblemPost> findPostWithUser(Long problemPostId);
    List<ProblemPost> findAllPostByProblemCode(Long problemTypeCode, int page);
    List<ProblemPost> searchPostByKeyword(String keyword,int page);
    List<ProblemPost> findAllPostByBookmark(Long loginId);
    void deletePost(Long problemPostId);
    void initProblemPost(Long problemPostId);

}
