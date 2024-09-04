package com.petweb.sponge.trainer.repository;

import com.petweb.sponge.trainer.domain.Trainer;

import java.util.Optional;

public interface TrainerRepositoryCustom {

    Optional<Trainer> findTrainerWithAddress(Long trainerId);
    void deleteTrainer(Long trainerId);
    void initTrainer(Long trainerId);
}
