package com.petweb.sponge.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HistoryDTO {

    private String title;
    private String startDt;
    private String endDt;
    private String description;
}
