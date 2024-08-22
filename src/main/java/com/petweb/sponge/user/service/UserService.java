package com.petweb.sponge.user.service;

import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.domain.UserAddress;
import com.petweb.sponge.user.dto.UserDTO;
import com.petweb.sponge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 단건 조회
     * @param userId
     * @return
     */
    public UserDTO findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("NO Found USER"));
            return toDto(user);
    }

    /**
     * 유저 정보 저장
     *
     * @param loginId
     * @param userDTO
     * @return
     */
    @Transactional
    public UserDTO saveUser(Long loginId, UserDTO userDTO) {
        //현재 로그인 유저 정보 가져오기
        User user = userRepository.findById(loginId).orElseThrow(
                () -> new RuntimeException("NO Found USER"));

        user.settingUser(userDTO);

        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    private UserDTO toDto(User user) {
        List<AddressDTO> addressDTOList = user.getUserAddresses().stream().map(userAddress -> AddressDTO.builder()
                .city(userAddress.getCity())
                .town(userAddress.getTown())
                .build()).collect(Collectors.toList());
        return UserDTO.builder()
                .name(user.getName())
                .gender(user.getGender())
                .phone(user.getPhone())
                .profileImgUrl(user.getProfileImgUrl())
                .addressList(addressDTOList)
                .build();
    }


}
