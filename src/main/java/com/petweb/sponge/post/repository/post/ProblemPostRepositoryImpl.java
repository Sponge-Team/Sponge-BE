package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.ProblemPost;
import com.petweb.sponge.post.domain.post.QBookmark;
import com.petweb.sponge.post.domain.post.QProblemPost;
import com.petweb.sponge.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.petweb.sponge.pet.domain.QPet.*;
import static com.petweb.sponge.post.domain.post.QBookmark.*;
import static com.petweb.sponge.post.domain.post.QPostCategory.*;
import static com.petweb.sponge.post.domain.post.QPostImage.*;
import static com.petweb.sponge.post.domain.post.QPostRecommend.*;
import static com.petweb.sponge.post.domain.post.QProblemPost.*;
import static com.petweb.sponge.post.domain.post.QTag.*;
import static com.petweb.sponge.user.domain.QUser.*;

public class ProblemPostRepositoryImpl implements ProblemPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProblemPostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ProblemPost findPostWithType(Long problemPostId) {
        return queryFactory
                .selectFrom(problemPost)
                .leftJoin(problemPost.postCategories, postCategory).fetchJoin() // PostCategory 정보 조인
                .where(problemPost.id.eq(problemPostId))
                .fetchOne();
    }

    @Override
    public ProblemPost findPostWithUser(Long problemPostId) {
        return queryFactory
                .selectFrom(problemPost)
                .leftJoin(problemPost.user, user).fetchJoin()
                .where(problemPost.id.eq(problemPostId))
                .fetchOne();
    }
    private static final int PAGE_SIZE = 10;  // 페이지당 항목 수

    @Override
    public List<ProblemPost> findAllPostByProblemCode(Long problemTypeCode,int page) {
        // 페이지 번호와 페이지 크기를 계산
        int offset = page * PAGE_SIZE;
        // 카테고리에 해당하는 글 ID 조회
        List<Long> problemPostIds = queryFactory
                .select(postCategory.problemPost.id)
                .from(postCategory)
                .where(postCategory.problemType.code.eq(problemTypeCode))
                .orderBy(postCategory.problemPost.createdAt.desc())  // 최신순으로 정렬
                .offset(offset)
                .limit(PAGE_SIZE)
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
        //북마크 삭제
        queryFactory
                .delete(bookmark)
                .where(bookmark.problemPost.id.eq(problemPostId))
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
