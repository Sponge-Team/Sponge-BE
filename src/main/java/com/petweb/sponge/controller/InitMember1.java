package com.petweb.sponge.controller;

import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.TrainerRepository;
import com.petweb.sponge.user.repository.UserRepository;
import com.petweb.sponge.utils.Gender;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitMember1 {
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
            Trainer trainer = new Trainer("test");
            trainerRepository.save(trainer);
        }
    }
}
