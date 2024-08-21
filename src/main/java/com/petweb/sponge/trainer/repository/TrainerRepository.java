package com.petweb.sponge.trainer.repository;

import com.petweb.sponge.trainer.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,Long>, TrainerRepositoryCustom {

    Optional<Trainer> findByEmail(String email);
}
