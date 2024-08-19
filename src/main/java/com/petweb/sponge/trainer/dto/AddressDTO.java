package com.petweb.sponge.trainer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDTO {
    private int city;
    private int town;
}
