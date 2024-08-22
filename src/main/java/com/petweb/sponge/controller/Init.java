package com.petweb.sponge.controller;

import com.petweb.sponge.post.domain.ProblemType;
import com.petweb.sponge.post.repository.ProblemTypeRepository;
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

        private final ProblemTypeRepository problemTypeRepository;

        @Transactional
        public void init() {
            ProblemType problemType1 = new ProblemType(100L, "분리불안");
            ProblemType problemType2 = new ProblemType(200L, "짖음");
            ProblemType problemType3 = new ProblemType(300L, "경계");
            ProblemType problemType4 = new ProblemType(400L, "사회");
            problemTypeRepository.save(problemType1);
            problemTypeRepository.save(problemType2);
            problemTypeRepository.save(problemType3);
            problemTypeRepository.save(problemType4);
        }
    }
}
