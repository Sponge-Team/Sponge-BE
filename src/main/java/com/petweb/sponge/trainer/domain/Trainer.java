package com.petweb.sponge.trainer.domain;

import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.trainer.dto.HistoryDTO;
import com.petweb.sponge.trainer.dto.TrainerDetailDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "trainers")
@EntityListeners(AuditingEntityListener.class)
public class Trainer {

    @Id
    @GeneratedValue
    private Long id;
    private String email; //로그인 아이디
    private String name; //이름
    private int gender = 1; //성별
    private String phone; //핸드폰 번호
    private String profileImgUrl; //프로필 이미지 링크
    private String content; //자기소개
    private int years; //연차

    private int adopt_count; // 채택 답변 수
    private int chat_count; // 1대1 채팅 수
    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TrainerAddress> trainerAddresses = new ArrayList<>();

    public void increaseAdoptCount() {
        this.adopt_count++;
    }

    public void decreaseAdoptCount() {
        this.adopt_count--;
    }

    @Builder
    public Trainer(String email, String name) {
        this.email = email;
        this.name = name;
    }

    //==생성, 수정 메서드==//
    public Trainer settingTrainer(TrainerDetailDTO trainerDetailDTO) {
        this.name = trainerDetailDTO.getName();
        this.gender = trainerDetailDTO.getGender();
        this.phone = trainerDetailDTO.getPhone();
        this.profileImgUrl = trainerDetailDTO.getProfileImgUrl();
        this.content = trainerDetailDTO.getContent();
        this.years = trainerDetailDTO.getYears();
        getHistories().clear();
        getTrainerAddresses().clear();

        //경력 정보 저장
        List<HistoryDTO> historyList = trainerDetailDTO.getHistoryList();
        for (HistoryDTO historyDTO : historyList) {
            History history = History.builder()
                    .title(historyDTO.getTitle())
                    .startDt(historyDTO.getStartDt())
                    .endDt(historyDTO.getEndDt())
                    .description(historyDTO.getDescription())
                    .trainer(this)
                    .build();
            getHistories().add(history);
        }
        //활동지역 정보 저장
        List<AddressDTO> addressList = trainerDetailDTO.getAddressList();
        for (AddressDTO addressDTO : addressList) {
            TrainerAddress trainerAddress = TrainerAddress.builder()
                    .city(addressDTO.getCity())
                    .town(addressDTO.getTown())
                    .trainer(this)
                    .build();
            getTrainerAddresses().add(trainerAddress);
        }
        return this;
    }

}
