package com.petweb.sponge.user.domain;

import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.user.dto.UserDTO;
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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email; //로그인 아이디
    private String name; //이름
    private int gender; //성별
    private String phone; //핸드폰 번호
    private String profileImgUrl; //프로필 이미지 링크

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> userAddresses = new ArrayList<>();

    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;

    @Builder
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    //==생성 메서드==//
    public User settingUser(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.gender = userDTO.getGender();
        this.phone = userDTO.getPhone();
        this.profileImgUrl = userDTO.getProfileImgUrl();
        List<AddressDTO> addressList = userDTO.getAddressList();

        //유저 지역 저장
        for (AddressDTO addressDTO : addressList) {
            UserAddress userAddress = UserAddress.builder()
                    .city(addressDTO.getCity())
                    .town(addressDTO.getTown())
                    .user(this)
                    .build();
            getUserAddresses().add(userAddress);
        }
        return this;
    }
}
