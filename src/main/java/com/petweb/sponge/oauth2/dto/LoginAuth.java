package com.petweb.sponge.oauth2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginAuth {
    private Long id;
    private String name;
    private String loginType;
}
