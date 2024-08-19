package com.petweb.sponge.post.controller;

import com.petweb.sponge.post.domain.ProblemType;
import com.petweb.sponge.post.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;


    /**
     * 문제행동유형 프론트로 데이터 전달
     * @return
     */
    @GetMapping("/problem_type")
    public ResponseEntity<List<ProblemType>> getProblemType() {
        List<ProblemType> problemType = categoryService.findAllProblemType();
        return new ResponseEntity<>(problemType,HttpStatus.OK);
    }




}
