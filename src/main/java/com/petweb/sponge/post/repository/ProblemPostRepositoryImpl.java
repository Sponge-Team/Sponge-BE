package com.petweb.sponge.post.repository;

import com.petweb.sponge.post.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.petweb.sponge.pet.domain.QPet.*;
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
    public ProblemPost findPostWithType(Long problemPostId) {
        return queryFactory
                .selectFrom(problemPost)
                .leftJoin(problemPost.pet, pet).fetchJoin()                    // Pet 정보 조인
                .leftJoin(problemPost.postCategories, postCategory).fetchJoin() // PostCategory 정보 조인
                .where(problemPost.id.eq(problemPostId))
                .fetchOne();

    }

    public List<ProblemPost> findAllPostByProblemCode(Long problemTypeCode) {
        List<Long> problemPostIds = queryFactory
                .select(postCategory.problemPost.id)
                .from(postCategory)
                .where(postCategory.problemType.code.eq(problemTypeCode))
                .fetch();
        if (problemPostIds.isEmpty()) {
            return new ArrayList<>();  // 결과가 없으면 빈 리스트 반환
        }

        return queryFactory
                .selectDistinct(problemPost)
                .from(problemPost)
                .leftJoin(problemPost.postCategories, postCategory).fetchJoin()
                .where(problemPost.id.in(problemPostIds))  // IN 절 사용
                .fetch();
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
