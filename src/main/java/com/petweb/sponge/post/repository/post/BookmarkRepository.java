package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    @Query("SELECT bm FROM Bookmark bm WHERE bm.problemPost.id = :problemPostId AND bm.user.id = :userId")
    Optional<Bookmark> findBookmark(@Param("problemPostId") Long problemPostId, @Param("userId") Long loginId);
}
