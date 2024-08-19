package com.petweb.sponge.post.service;

import com.petweb.sponge.post.domain.ProblemType;
import com.petweb.sponge.post.repository.ProblemTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final ProblemTypeRepository problemTypeRepository;


    public List<ProblemType> findAllProblemType() {
        return problemTypeRepository.findAll();
    }
}
