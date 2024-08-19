package com.petweb.sponge.controller;

import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Init {
    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }
    @Component
    @RequiredArgsConstructor
    static class InitMemberService {

        private final TrainerRepository trainerRepository;

        @Transactional
        public void init() {
            Trainer trainer = Trainer.builder()
                    .email("test")
                    .build();
            trainerRepository.save(trainer);
        }
    }
}
