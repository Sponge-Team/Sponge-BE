package com.petweb.sponge.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
//JWT 전 사용하는 임시 클래스
public class TrainerId {
    private Long userId;
    private Long trainerId;

}
