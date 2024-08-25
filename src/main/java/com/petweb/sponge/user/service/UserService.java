package com.petweb.sponge.user.service;

import com.amazonaws.services.kms.model.NotFoundException;
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
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserDTO findUser(Long userId) {
        // user,address 한번에 조회
        User user = userRepository.findUserWithAddress(userId);
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
                () -> new NotFoundException("NO Found USER"));

        user.settingUser(userDTO);

        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    /**
     * 유저 정보 삭제 (FK관련해서 삭제할 시 수정 필요)
     *
     * @param userId
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("NO Found USER"));
        userRepository.deleteUser(user.getId());
    }

    private UserDTO toDto(User user) {
        List<AddressDTO> addressDTOList = user.getUserAddresses().stream().map(userAddress -> AddressDTO.builder()
                .city(userAddress.getCity())
                .town(userAddress.getTown())
                .build()).collect(Collectors.toList());
        return UserDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .phone(user.getPhone())
                .profileImgUrl(user.getProfileImgUrl())
                .addressList(addressDTOList)
                .build();
    }


}
