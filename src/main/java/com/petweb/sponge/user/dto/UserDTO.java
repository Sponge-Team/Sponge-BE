package com.petweb.sponge.user.dto;

import com.petweb.sponge.trainer.dto.AddressDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDTO {

    private Long userId;
    private String name;
    private int gender;
    private String phone;
    private String profileImgUrl;

    private List<AddressDTO> addressList;
}
