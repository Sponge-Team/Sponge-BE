package com.petweb.sponge.trainer.repository;

import com.petweb.sponge.trainer.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.user WHERE r.trainer.id = :trainerId")
    List<Review> findAllByTrainerId(@Param("trainerId") Long trainerId);
}
