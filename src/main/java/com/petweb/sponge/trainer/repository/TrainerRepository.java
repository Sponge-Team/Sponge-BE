package com.petweb.sponge.trainer.repository;

import com.petweb.sponge.trainer.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer,Long>, TrainerRepositoryCustom {

}
