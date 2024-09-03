package com.petweb.sponge.trainer.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TrainerDetailDTO {

    private Long trainerId; //훈련사 아이디
    private String name; //이름
    private int gender; //성별
    private String phone; //핸드폰 번호
    private String profileImgUrl; //프로필이미지링크
    private String content; //자기소개
    private int years; //연차
    private int adoptCount; // 채택답변수
    private int chatCount; // 상담횟수
    private List<AddressDTO> addressList;
    private List<HistoryDTO> historyList;

}
