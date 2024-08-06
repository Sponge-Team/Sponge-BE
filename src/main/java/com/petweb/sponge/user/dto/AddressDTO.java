package com.petweb.sponge.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDTO {
    private int city;
    private int town;
}
