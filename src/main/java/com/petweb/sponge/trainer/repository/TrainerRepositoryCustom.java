package com.petweb.sponge.trainer.repository;

import com.petweb.sponge.trainer.domain.Trainer;

public interface TrainerRepositoryCustom {

    Trainer findTrainerWithAddress(Long trainerId);
    void deleteTrainer(Long trainerId);
}
