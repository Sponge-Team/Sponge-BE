package com.petweb.sponge.user.dto;

import com.petweb.sponge.trainer.dto.AddressDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class UserUpdateDTO {

    private String userName;

    private List<AddressDTO> addressList;
}
