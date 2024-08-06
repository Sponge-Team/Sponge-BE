package com.petweb.sponge.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class TrainerId {
    private Long userId;
    private Long trainerId;

}
