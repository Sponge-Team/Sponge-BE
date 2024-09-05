package com.petweb.sponge.user.dto;

import com.petweb.sponge.trainer.dto.AddressDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDTO {

    private Long userId;
    private String userName;
    private String petName; // 반려견 이름
    private String breed; // 견종
    private int gender; // 성별
    private int age; // 나이
    private float weight; // 몸무게
    private String petImgUrl; //펫 이미지
    private List<AddressDTO> addressList;
}
