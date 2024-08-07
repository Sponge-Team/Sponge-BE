package com.petweb.sponge.user.domain;

import com.petweb.sponge.user.dto.AddressDTO;
import com.petweb.sponge.user.dto.HistoryDTO;
import com.petweb.sponge.user.dto.TrainerDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private int years;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "trainer",cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @Builder
    public Trainer(String content, int years, User user) {
        this.content = content;
        this.years = years;
        this.user = user;
    }

    //==연관관계 메서드==//
    public void addHistory(History history) {
        histories.add(history);
        history.setTrainer(this);
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setTrainer(this);
    }

    //==생성 메서드==//
    public static Trainer createTrainer(TrainerDTO trainerDTO, User user) {
        Trainer trainer = Trainer.builder()
                .content(trainerDTO.getContent())
                .years(trainerDTO.getYears())
                .user(user)
                .build();

        List<HistoryDTO> historyList = trainerDTO.getHistoryList();

        //경력 정보 저장
        for (HistoryDTO historyDTO : historyList) {
            History history = History.builder()
                    .title(historyDTO.getTitle())
                    .startDt(historyDTO.getStartDt())
                    .endDt(historyDTO.getEndDt())
                    .description(historyDTO.getDescription()).build();
            trainer.addHistory(history);
        }
        //활동지역 정보 저장
        List<AddressDTO> addressList = trainerDTO.getAddressList();
        for (AddressDTO addressDTO : addressList) {
            Address address = Address.builder()
                    .city(addressDTO.getCity())
                    .town(addressDTO.getTown())
                    .build();
            trainer.addAddress(address);
        }
        return trainer;
    }

}
