package com.petweb.sponge.user.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.exception.error.NotFoundUser;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.UserDetailDTO;
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
    public UserDetailDTO findUser(Long userId) {
        // user,address 한번에 조회
        User user = userRepository.findUserWithAddress(userId).orElseThrow(
                NotFoundUser::new);
        return toDto(user);
    }

    /**
     * 유저 내정보 조회
     * @param loginId
     * @return
     */
    @Transactional(readOnly = true)
    public UserDetailDTO findMyInfo(Long loginId) {
        // user,address 한번에 조회
        User user = userRepository.findUserWithAddress(loginId).orElseThrow(
                NotFoundUser::new);
        return toDto(user);
    }

    /**
     * 유저 정보 저장
     *
     * @param loginId
     * @param userDetailDTO
     * @return
     */
    @Transactional
    public void saveUser(Long loginId, UserDetailDTO userDetailDTO) {
        //현재 로그인 유저 정보 가져오기
        User user = userRepository.findById(loginId).orElseThrow(
                NotFoundUser::new);

        user.settingUser(userDetailDTO);
        userRepository.save(user);
    }

    /**
     * 유저 정보 수정
     *
     * @param userId
     * @param userDetailDTO
     */
    @Transactional
    public void updateUser(Long userId, UserDetailDTO userDetailDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                NotFoundUser::new);
        user.settingUser(userDetailDTO);
    }

    /**
     * 유저 정보 삭제
     *
     * @param userId
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                NotFoundUser::new);
        userRepository.deleteUser(user.getId());
    }

    private UserDetailDTO toDto(User user) {
        List<AddressDTO> addressDTOList = user.getUserAddresses().stream().map(userAddress -> AddressDTO.builder()
                .city(userAddress.getCity())
                .town(userAddress.getTown())
                .build()).collect(Collectors.toList());
        return UserDetailDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .phone(user.getPhone())
                .profileImgUrl(user.getProfileImgUrl())
                .addressList(addressDTOList)
                .build();
    }



}
