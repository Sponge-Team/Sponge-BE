package com.petweb.sponge.user.repository;

import com.petweb.sponge.user.domain.Trainer;

public interface TrainerRepositoryCustom {


    Trainer findByTrainerId(Long trainerId);
}
