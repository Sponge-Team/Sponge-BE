package com.petweb.sponge.trainer.domain;

import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.trainer.dto.HistoryDTO;
import com.petweb.sponge.trainer.dto.TrainerDTO;
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
    private int gender; //성별
    private String phone; //핸드폰 번호
    private String profileImgUrl; //프로필 이미지 링크
    private String content; //자기소개
    private int years; //연차
    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    //==연관관계 메서드==//
    public void addHistory(History history) {
        histories.add(history);
        history.setTrainer(this);
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setTrainer(this);
    }

    @Builder
    public Trainer(String email) {
        this.email = email;
    }

    //==생성 메서드==//
    public  Trainer settingTrainer(TrainerDTO trainerDTO) {
        this.name = trainerDTO.getName();
        this.gender = trainerDTO.getGender();
        this.phone = trainerDTO.getPhone();
        this.profileImgUrl = trainerDTO.getProfileImgUrl();
        this.content = trainerDTO.getContent();
        this.years = trainerDTO.getYears();
        List<HistoryDTO> historyList = trainerDTO.getHistoryList();
        //경력 정보 저장
        for (HistoryDTO historyDTO : historyList) {
            History history = History.builder()
                    .title(historyDTO.getTitle())
                    .startDt(historyDTO.getStartDt())
                    .endDt(historyDTO.getEndDt())
                    .description(historyDTO.getDescription()).build();
            this.addHistory(history);
        }
        //활동지역 정보 저장
        List<AddressDTO> addressList = trainerDTO.getAddressList();
        for (AddressDTO addressDTO : addressList) {
            Address address = Address.builder()
                    .city(addressDTO.getCity())
                    .town(addressDTO.getTown())
                    .build();
            this.addAddress(address);
        }
        return this;
    }

}
