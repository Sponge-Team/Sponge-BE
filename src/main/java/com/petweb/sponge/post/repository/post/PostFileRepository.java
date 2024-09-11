package com.petweb.sponge.post.repository.post;

import com.petweb.sponge.post.domain.post.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostFileRepository extends JpaRepository<PostFile,Long> {

    @Modifying
    @Query("DELETE FROM PostFile pf WHERE pf.fileUrl IN :fileUrlList")
    void deleteByFiles(List<String> fileUrlList);
}
