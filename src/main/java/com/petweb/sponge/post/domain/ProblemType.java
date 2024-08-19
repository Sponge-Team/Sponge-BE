package com.petweb.sponge.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "problem_type")
public class ProblemType {

    @Id
    private Long code; //문제유형 코드
    private String description; //문제유형

    @Builder
    public ProblemType(Long code, String description) {
        this.code = code;
        this.description = description;
    }
}
