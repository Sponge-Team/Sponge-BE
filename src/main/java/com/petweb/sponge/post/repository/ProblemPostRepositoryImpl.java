package com.petweb.sponge.post.repository;

import com.petweb.sponge.post.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.petweb.sponge.post.domain.QPostCategory.*;
import static com.petweb.sponge.post.domain.QPostImage.*;
import static com.petweb.sponge.post.domain.QPostRecommend.*;
import static com.petweb.sponge.post.domain.QProblemPost.*;
import static com.petweb.sponge.post.domain.QTag.*;

public class ProblemPostRepositoryImpl implements ProblemPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProblemPostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deletePost(Long problemPostId) {
        //카테고리별 글 삭제
        queryFactory
                .delete(postCategory)
                .where(postCategory.problemPost.id.eq(problemPostId))
                .execute();
        //게시글 추천 삭제
        queryFactory
                .delete(postRecommend)
                .where(postRecommend.problemPost.id.eq(problemPostId))
                .execute();
        //해시태그 삭제
        queryFactory
                .delete(tag)
                .where(tag.problemPost.id.eq(problemPostId))
                .execute();
        //이미지 삭제
        queryFactory
                .delete(postImage)
                .where(postImage.problemPost.id.eq(problemPostId))
                .execute();
        //게시글 삭제
        queryFactory
                .delete(problemPost)
                .where(problemPost.id.eq(problemPostId))
                .execute();
    }
}
